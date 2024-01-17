package com.wizer.wizerassessment.exceptions;
public class CustomInternalServerException extends RuntimeException{
    public CustomInternalServerException(String message){
        super(message);
    }
}