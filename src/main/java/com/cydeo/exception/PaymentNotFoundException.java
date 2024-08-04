package com.cydeo.exception;

/**
 * author:AbduShukur
 * date:8/4/2024
 */
public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}