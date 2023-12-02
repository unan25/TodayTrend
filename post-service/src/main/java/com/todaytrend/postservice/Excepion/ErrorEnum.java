package com.todaytrend.postservice.Excepion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    UNKNOWN_POST_ISSUE(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다"),
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND,"없는 게시물 입니다"),
    NO_RIGHT(HttpStatus.FORBIDDEN,"권한이 없습니다");

    private final HttpStatus status;
    private final String message;
}
