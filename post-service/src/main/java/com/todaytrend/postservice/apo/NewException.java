package com.todaytrend.postservice.apo;

public class NewException extends RuntimeException{

    public NewException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }
}
