package com.nncompany.api.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestError {

    private HttpStatus status;
    private String message;
    private String description;

    public RequestError(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public RequestError(HttpStatus status, String message, String description){
        this.status = status;
        this.message = message;
        this.description = description;
    }
}
