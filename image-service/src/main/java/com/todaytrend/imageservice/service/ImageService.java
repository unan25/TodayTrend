package com.todaytrend.imageservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.todaytrend.imageservice.dto.request.RequestImageDto;
import com.todaytrend.imageservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //MultipartFile을 받아서 S3에 저장하고 해당 파일의 URL반환
    public String saveImage(MultipartFile multipartFile) throws IOException {
        // 1. 원본파일 이름 가져오기
        String originalFilename = multipartFile.getOriginalFilename();
        // 2. S3 객체의 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        // 3. S3에 파일 업로드
        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        // 4. S3에 업로드된 파일 Url을 String으로 변환
        String imageUrl = amazonS3.getUrl(bucket, originalFilename).toString();
        // 5. Dto를 Entity로 변환, 업로드된 파일의 URL을 Repository에 저장
        RequestImageDto requestImageDto = new RequestImageDto(imageUrl);
        imageRepository.save(requestImageDto.toEntity());
        return "이미지 저장 완료";
    }

//    public ResponseImageDto findImageByPostId(Long postId) {
//        imageRepository.findBy
//    }
}