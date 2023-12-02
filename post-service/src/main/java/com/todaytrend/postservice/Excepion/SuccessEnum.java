package com.todaytrend.postservice.Excepion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum SuccessEnum {
    OK(HttpStatus.OK, "요청 성공"),
    CREATE(HttpStatus.CREATED,"생성성공");

    private final HttpStatus status;
    private final String message;
}
