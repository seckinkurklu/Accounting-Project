package com.cydeo.exception;

/**
 * author:AbduShukur
 * date:8/4/2024
 */
public class InvoiceProductNotFoundException  extends RuntimeException {
    public InvoiceProductNotFoundException(String message) {
        super(message);
    }
}
