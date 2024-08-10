package com.cydeo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class,
            ClientVendorNotFoundException.class,
            CompanyNotFoundException.class,
            InsufficientStockException.class,
            InvoiceNotFoundException.class,
            InvoiceProductNotFoundException.class,
            PaymentNotFoundException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({CategoryNotFoundException.class,
//            ClientVendorNotFoundException.class,
//            CompanyNotFoundException.class,
//            InsufficientStockException.class,
//            InvoiceNotFoundException.class,
//            InvoiceProductNotFoundException.class,
//            PaymentNotFoundException.class,
//            RoleNotFoundException.class,
//            UserNotFoundException.class})
//    public ResponseEntity<String> handleUserNotFoundException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
////    public ModelAndView handleNotFoundExceptions(Exception ex) {
////        ModelAndView mav = new ModelAndView("error");
////        List<String> exceptionMessages = new ArrayList<>();
////        exceptionMessages.add(ex.getMessage());
////        mav.addObject("exceptionMessages", exceptionMessages);
////        return mav;
////    }
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleGenericException() {
//        ModelAndView mav = new ModelAndView("error");
//        mav.addObject("exceptionMessages", null);
//        return mav;
//    }
}

