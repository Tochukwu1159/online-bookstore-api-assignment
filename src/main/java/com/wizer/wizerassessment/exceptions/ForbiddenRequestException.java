package com.wizer.wizerassessment.exceptions;

public class ForbiddenRequestException extends RuntimeException{
    public ForbiddenRequestException(String message){
        super(message);
    }
}
