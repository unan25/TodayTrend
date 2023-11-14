package com.todaytrend.imageservice.controller;

import com.todaytrend.imageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {

     private final ImageService imageService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Image-service is available.";
    }

    // 이미지파일 S3업로드 후 DB에 URL저장
    @PostMapping("upload") //React에서 name="image"로 주면 RequestParam("image")
    public ResponseEntity<?> imageUpload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
       return new ResponseEntity<>(imageService.saveImage(multipartFile), HttpStatus.OK) ;
    }

//    // 이미지불러오기
//    @GetMapping("")
//    public ResponseEntity<?> getImageByImageID(@PathVariable ) {
//
//    }
}
