package com.services.microsoft.exception;

public class SecurityUpdateNotFoundException extends RuntimeException{

    public SecurityUpdateNotFoundException(String message){
        super(message);
    }

}
