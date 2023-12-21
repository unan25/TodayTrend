package com.todaytrend.imageservice.controller;

import com.todaytrend.imageservice.dto.request.RequestImageDto;
import com.todaytrend.imageservice.dto.request.RequestImageListDto;
import com.todaytrend.imageservice.dto.request.RequestQueryDto;
import com.todaytrend.imageservice.dto.response.ResponseImageListDto;
import com.todaytrend.imageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "Image-service is available.";
    }

    // 게시물 이미지 저장
    @PostMapping("")
    public ResponseEntity<?> imageUpload(@RequestParam(value = "postId") Long postId,
                                         @RequestPart(value = "images") MultipartFile[] images) throws IOException {
        return new ResponseEntity<>(imageService.uploadImages(postId, images), HttpStatus.OK);
    }

    // 게시물 이미지 조회
    @GetMapping("{postId}")
    public ResponseEntity<?> getImageByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(imageService.findImagesByPostId(postId), HttpStatus.OK);
    }

    // 게시물 이미지 삭제
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deleteImages(@PathVariable Long postId) {
        return new ResponseEntity<>(imageService.deleteImages(postId), HttpStatus.OK);
    }

    // 게시물 이미지 수정
    @PostMapping("{postId}")
    public ResponseEntity<?> updateImages(@PathVariable Long postId,
                                          @RequestPart MultipartFile[] images) throws IOException {
        return new ResponseEntity<>(imageService.updateImages(postId, images), HttpStatus.OK);
    }
    // 게시물 이미지 조회 (postIdList)
    @PostMapping("postList")
    public ResponseEntity<?> getImagesByPostIdList(@RequestBody RequestImageListDto dto) {
        List<Long> postIdList = dto.getPostIdList();
        return new ResponseEntity<>(imageService.findImageByPostIdList(postIdList) , HttpStatus.OK);
    }

    // 테스트용
    @GetMapping("test")
    public ResponseEntity<?> test(@ModelAttribute RequestQueryDto dto) {
        return new ResponseEntity<>(imageService.test(dto), HttpStatus.OK);
    }
    @PostMapping("test1")
    public ResponseEntity<?> test1(@RequestBody RequestQueryDto dto) {
        return new ResponseEntity<>(imageService.test(dto), HttpStatus.OK);
    }

    //프로필 이미지 등록
    @PostMapping("profile")
    public ResponseEntity<?> createProfileImage(@RequestPart MultipartFile image) throws IOException{
        return new ResponseEntity<>(imageService.createProfileImage(image), HttpStatus.OK);
    }
    //프로필 이미지 수정
    @PutMapping("profile")
    public ResponseEntity<?> updateProfileImage(@RequestPart("image") MultipartFile image,
                                              @RequestPart("imageUrl") String imageUrl) throws IOException {
        return new ResponseEntity<>(imageService.updateProfileImage(image, imageUrl) , HttpStatus.OK);
    }
    //게시물 이미지 파일 조회 (수정페이지)
    @GetMapping("update/{postId}")
    public ResponseEntity<?> getImages(@PathVariable Long postId) {
        return new ResponseEntity<>(imageService.getImages(postId) , HttpStatus.OK);
    }
}
