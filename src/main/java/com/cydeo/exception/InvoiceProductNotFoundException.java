package com.cydeo.exception;

public class InvoiceProductNotFoundException extends RuntimeException{

    public InvoiceProductNotFoundException(String message) {
        super(message);
    }

}
