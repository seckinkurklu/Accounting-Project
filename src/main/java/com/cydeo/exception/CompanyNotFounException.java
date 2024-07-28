package com.cydeo.exception;

public class CompanyNotFounException extends  RuntimeException {
   public CompanyNotFounException(String message){
        super(message);
    }
}
