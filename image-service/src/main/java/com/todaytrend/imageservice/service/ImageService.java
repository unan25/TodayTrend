package com.todaytrend.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.todaytrend.imageservice.dto.response.ResponseImageDto;
import com.todaytrend.imageservice.entity.Image;
import com.todaytrend.imageservice.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket; // S3 버킷 이름

    // 이미지 이름 중복 방지 UUID 붙이기
    private String changedImageName(String originName){
        String random = UUID.randomUUID().toString();
        return random+originName;
    }
    // 게시물 이미지 등록
    public ResponseImageDto uploadImages(Long postId , MultipartFile[] images) throws IOException {
        // 0. 반환할 이미지 dto 생성
        ResponseImageDto imageDto = new ResponseImageDto();
        imageDto.setPostId(postId);
        // 1. 이미지리스트에서 하나씩 S3에 저장 후 URL반환
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();
            String imageName = changedImageName(originalFilename);
            // 2. S3 객체의 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            //저장할 postId 폴더가 붙은 이름 = key
            String key =   postId + "/" + imageName;
            // 3. S3에 파일 업로드
            amazonS3.putObject(bucket, key, image.getInputStream(), metadata);
            // 4. S3에 업로드된 파일 Url을 String으로 변환
            String imageUrl = amazonS3.getUrl(bucket, key).toString();
            // 5.업로드된 파일의 URL과 postId Repository에 저장
            Image imageEntity = Image.builder()
                    .postId(postId)
                    .imageUrl(imageUrl).build();
            imageRepository.save(imageEntity);
            // 6. imageUrl dto에 넣어주기
            imageDto.getImageUrlList().add(imageUrl);
        }
        return imageDto;
    }

    // 게시물 이미지 삭제
    @Transactional
    public  String deleteImages(Long postId){
        List<Image> images = imageRepository.findImageByPostId(postId);
        imageRepository.deleteImageByPostId(postId);
        for (Image image : images) {
            String key = image.getImageUrl().split(".com/")[1]; //.com/ 이후의 파일명 반환
            amazonS3.deleteObject(bucket,key);
        }
    return "게시물 이미지 삭제 완료";
    }
    // 게시물 이미지 조회
    public ResponseImageDto findImageByPostId(Long postId) {
        List<Image> images = imageRepository.findImageByPostId(postId);
        List<String> imageUrlList = new ArrayList<>();
        for (Image image : images) {
            imageUrlList.add(image.getImageUrl());
        }
        return ResponseImageDto.builder()
                .imageUrlList(imageUrlList)
                .postId(postId)
                .build();
    }
    // 게시물 이미지 수정
    @Transactional
    public ResponseImageDto updateImages(Long postId, MultipartFile[] images) throws IOException {
        deleteImages(postId);
        return uploadImages(postId, images);
    }
}