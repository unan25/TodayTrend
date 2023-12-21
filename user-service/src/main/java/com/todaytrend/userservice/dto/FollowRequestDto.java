package com.todaytrend.userservice.dto;

import com.todaytrend.userservice.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowRequestDto {

    private String followerId;
    private String followingId;

    public Follow toEntity() {

        return Follow.builder()
                .followerId(this.followerId)
                .followingId(this.followingId)
                .build();

    }


}
