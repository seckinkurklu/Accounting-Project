package com.cydeo.exception;

/**
 * author:AbduShukur
 * date:8/4/2024
 */
public class InsufficientStockException  extends RuntimeException{

    public InsufficientStockException(String message){
        super(message);
    }
}