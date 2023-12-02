package com.todaytrend.postservice.Excepion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> testException(final CustomException e){
        return new ResponseEntity<>(new ResponseDto(e.getErrorEnum()), e.getErrorEnum().getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> testAllTypeException(final RuntimeException e){
        return new ResponseEntity<>(new ResponseDto(ErrorEnum.UNKNOWN_POST_ISSUE),ErrorEnum.UNKNOWN_POST_ISSUE.getStatus());
    }

}
