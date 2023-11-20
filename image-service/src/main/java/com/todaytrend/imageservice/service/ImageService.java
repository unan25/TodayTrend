package com.todaytrend.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.todaytrend.imageservice.dto.request.RequestImageDto;
import com.todaytrend.imageservice.dto.response.ResponseImageDto;
import com.todaytrend.imageservice.entity.Image;
import com.todaytrend.imageservice.repository.ImageRepository;
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
    private String bucket;

    // 이미지 이름 중복 방지 UUID 붙이기
    private String changedImageName(String originName){
        String random = UUID.randomUUID().toString();
        return random+originName;
    }
    // 이미지 업로드 Dto에서 이미지리스트만 받기
    public List<MultipartFile> getImageList(RequestImageDto requestImageDto) {
        return requestImageDto.getImages().stream().toList();
    }

    //MultipartFile을 받아서 S3에 저장하고 해당 파일의 URL반환
    public String uploadImageToS3(RequestImageDto requestImageDto) throws IOException {
        List<MultipartFile> imageList = getImageList(requestImageDto);
        // 1. 이미지리스트에서 하나씩 S3에 저장 후 URL반환
        for (MultipartFile image : imageList) {
            String originalFilename = image.getOriginalFilename();
            String imageName = changedImageName(originalFilename);
            // 2. S3 객체의 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            //저장할 postId 폴더
            String folderName =   requestImageDto.getPostId() + "/" + imageName;
            // 3. S3에 파일 업로드
            amazonS3.putObject(bucket, folderName, image.getInputStream(), metadata);
            // 4. S3에 업로드된 파일 Url을 String으로 변환
            String imageUrl = amazonS3.getUrl(bucket, imageName).toString();
            // 5. Dto를 Entity로 변환, 업로드된 파일의 URL과 postId Repository에 저장
            Image imageEntity = Image.builder()
                    .postId(requestImageDto.getPostId())
                    .imageUrl(imageUrl).build();
            imageRepository.save(imageEntity);
        }
        return "게시물 이미지 저장 완료";
    }

    //postId로 image 조회 후 imageUrl 리스트 반환
    public ResponseImageDto findImageByPostId(Long postId) {
        List<Image> images = imageRepository.findImageByPostId(postId);
        List<String> imageUrlList = new ArrayList<>();
        for (Image image : images) {
            imageUrlList.add(image.getImageUrl());
        }
        ResponseImageDto responseImageDto = ResponseImageDto.builder()
                .imageUrlList(imageUrlList)
                .postId(postId)
                .build();
        return responseImageDto;
    }

    // multiPartFile 받아서 S3업로드 후 이미지 URL만 반환하는 로직
    public void uploadImage(MultipartFile image) {
        String originalName = image.getOriginalFilename();
        String imageName = changedImageName(originalName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        String folderName =   "user" + "/" + imageName;

    }
}