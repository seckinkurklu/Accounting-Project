package com.cydeo.exception;

/**
 * author:AbduShukur
 * date:8/4/2024
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}