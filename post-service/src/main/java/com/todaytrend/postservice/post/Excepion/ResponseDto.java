package com.todaytrend.postservice.post.Excepion;

import lombok.Getter;

@Getter
public class ResponseDto {
    private final int status;
    private final String message;
    private final Object data;

    public ResponseDto(ErrorEnum errorEnum){
        this.status = errorEnum.getStatus().value();
        this.message = errorEnum.getMessage();
        this.data = null;
    }

    public ResponseDto(SuccessEnum successEnum,Object data){
        this.status = successEnum.getStatus().value();
        this.message = successEnum.getMessage();
        this.data = data;
    }
}
