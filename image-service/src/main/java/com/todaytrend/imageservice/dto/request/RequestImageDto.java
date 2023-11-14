package com.todaytrend.imageservice.dto.request;

import com.todaytrend.imageservice.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestImageDto {

    private String imageUrl;

    public Image toEntity() {
        return Image.builder()
                .imageUrl(this.imageUrl)
                .build();
    }
}
