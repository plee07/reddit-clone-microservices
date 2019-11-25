package com.ga.commentapi.exception;

public class errorResponse {

    private int status;
    private String message;

    public errorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}