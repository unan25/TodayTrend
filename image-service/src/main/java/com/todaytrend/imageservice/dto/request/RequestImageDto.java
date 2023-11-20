package com.todaytrend.imageservice.dto.request;

import com.todaytrend.imageservice.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestImageDto { // 이미지 업로드용 Dto

    private List<MultipartFile> images;

    private String imageUrl;
    private Long postId;

    public Image toEntity() {
        return Image.builder()
                .postId(this.postId)
                .imageUrl(this.imageUrl)
                .build();
    }
}
