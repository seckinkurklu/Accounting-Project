package com.cydeo.exception;

/**
 * author:AbduShukur
 * date:7/30/2024
 */
public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message) {
        super(message);
    }
}