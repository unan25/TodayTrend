package com.todaytrend.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.todaytrend.imageservice.dto.request.RequestQueryDto;
import com.todaytrend.imageservice.dto.response.ResponseImageDto;
import com.todaytrend.imageservice.dto.response.ResponseImageListDto;
import com.todaytrend.imageservice.dto.response.ResponseProfileImageDto;
import com.todaytrend.imageservice.entity.Image;
import com.todaytrend.imageservice.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public String uploadImages(Long postId , MultipartFile[] images) throws IOException {
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
        }
        return "이미지 저장완료";
    }

    // 게시물 이미지 삭제
    @Transactional
    public  String deleteImages(Long postId){
        List<Image> images = imageRepository.findImagesByPostId(postId);
        imageRepository.deleteImageByPostId(postId);
        for (Image image : images) {
            String decodeUrl = decodeUrl(image.getImageUrl());
            String key = decodeUrl.split(".com/")[1];
            System.out.println("decodeUrl = " + decodeUrl);
            System.out.println("key = " + key);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket,key));
        }
    return "게시물 이미지 삭제 완료";
    }

    // 게시물 이미지 조회
    public ResponseImageDto findImagesByPostId(Long postId) {
        List<Image> images = imageRepository.findImagesByPostId(postId);
        List<String> imageUrlList = new ArrayList<>();
        for (Image image : images) {
            imageUrlList.add(image.getImageUrl());
        }
        return ResponseImageDto.builder()
                .imageUrlList(imageUrlList)
                .postId(postId)
                .build();
    }
    // 게시물 썸네일 이미지 조회
    public ResponseImageListDto findImageByPostIdList(List<Long> postIdList) {
        List<ResponseImageDto> data = new ArrayList<>();
        for (Long postId : postIdList) {
            Image image = imageRepository.findFirstByPostId(postId);
            if(image == null) {
                continue;
            }
            data.add(ResponseImageDto.builder()
                            .postId(postId)
                            .imageUrl(image.getImageUrl())
                    .build());
        }
        return ResponseImageListDto.builder().data(data).build();
    }

    // 게시물 이미지 수정
    @Transactional
    public String updateImages(Long postId, MultipartFile[] images) throws IOException {
        deleteImages(postId);
        uploadImages(postId, images);
        return "이미지 수정 완료";
    }

    //프로필 이미지 등록
    public ResponseProfileImageDto createProfileImage(MultipartFile image) throws IOException {
            String originalFilename = image.getOriginalFilename();
            String imageName = changedImageName(originalFilename);
            // 2. S3 객체의 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());
            //저장할 profile 폴더가 붙은 이름 = key
            String key =   "profile" + "/" + imageName;
            // 3. S3에 파일 업로드
            amazonS3.putObject(bucket, key, image.getInputStream(), metadata);
            // 4. S3에 업로드된 파일 Url을 String으로 변환
            String imageUrl = amazonS3.getUrl(bucket, key).toString();
            // 6. imageUrl dto에 넣어주기
           return ResponseProfileImageDto.builder().profileImage(imageUrl).build();
    }
    //프로필 이미지 수정
    public ResponseProfileImageDto updateProfileImage(MultipartFile image, String imageUrl) throws IOException {
        // 원래 이미지 삭제
        String key = imageUrl.split(".com/")[1]; //.com/ 이후의 파일명 반환
        amazonS3.deleteObject(bucket,key);

        // 다시 프로필 이미지 등록
        return createProfileImage(image);
    }
    //프로필 이미지 삭제
    public ResponseProfileImageDto deleteProfileImage(String imageUrl) throws IOException {
        // 원래 이미지 삭제
        String decodeUrl = decodeUrl(imageUrl);
        String key = decodeUrl.split(".com/")[1];
        amazonS3.deleteObject(bucket,key);
        return ResponseProfileImageDto.builder()
                .profileImage("https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg")
                .build();
    }

    // 이미지 파일 다운로드 (게시물 이미지 수정페이지 조회)
    public List<S3Object> getImages(Long postId) {
        List<Image> images = imageRepository.findImagesByPostId(postId);
        List<S3Object> imageList = new ArrayList<>();
        for (Image image : images) {
            String decodeUrl = decodeUrl(image.getImageUrl());
            String key = decodeUrl.split(".com/")[1];
            imageList.add(amazonS3.getObject(bucket, key));
        }
        return imageList;
    }

    // s3 url 키로 반환
    private static String decodeUrl(String url) {
        try {
            return URLDecoder.decode(url,"UTF-8");
        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URL 디코딩 중 오류 발생, e");
        }
    }
}