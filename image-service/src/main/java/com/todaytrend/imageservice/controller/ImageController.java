package com.todaytrend.imageservice.controller;

import com.todaytrend.imageservice.dto.request.RequestImageDto;
import com.todaytrend.imageservice.dto.response.ResponseImageDto;
import com.todaytrend.imageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

     private final ImageService imageService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Image-service is available.";
    }

    // 이미지파일 S3업로드 후 DB에 URL저장
    @PostMapping("upload")
    public ResponseEntity<?> imageUpload(@RequestPart(value = "postId" ,required = false) String postId,
    @RequestPart(value = "images") MultipartFile[] images) throws IOException {
        // 프론트에서 주는 데이터형식에 따라 수정 필요 (이건 이미지리스트 + postId만 줄때)
        RequestImageDto requestImageDto = new RequestImageDto();
        requestImageDto.setImages(List.of(images));
        requestImageDto.setPostId(Long.parseLong(postId));
       return new ResponseEntity<>(imageService.uploadImageToS3(requestImageDto), HttpStatus.OK) ;
    }

    // imageUrlList전달
    @GetMapping("{postId}")
    public ResponseEntity<?> getImageByPostID(@PathVariable Long postId ) {
        return new ResponseEntity<>(imageService.findImageByPostId(postId), HttpStatus.OK);
    }

    // 테스트용
    @PostMapping("test")
    public ResponseEntity<?> test(@RequestPart("images")ResponseImageDto dto) {
        System.out.println("dto :" + dto);
        return new ResponseEntity<>("Successfully received data", HttpStatus.OK);
    }
}
