package com.wizer.wizerassessment.exceptions;

public class CustomOutOfStock extends RuntimeException{
    public CustomOutOfStock(String message){
        super(message);
    }
}
