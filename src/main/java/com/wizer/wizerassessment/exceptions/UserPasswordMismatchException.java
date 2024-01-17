package com.wizer.wizerassessment.exceptions;

public class UserPasswordMismatchException extends RuntimeException{
    public UserPasswordMismatchException(String message){
        super(message);
    }
}
