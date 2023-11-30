package com.todaytrend.postservice.comment.dto.response;

import com.todaytrend.postservice.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseCommentListDto {
    private List<Comment> commentList;

    // 유저 서버에서 받아 오는 필드
    private String nickname;
    private String profileImage;
}
