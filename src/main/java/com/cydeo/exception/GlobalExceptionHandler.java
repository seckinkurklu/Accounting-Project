package com.cydeo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * author:AbduShukur
 * date:8/4/2024
 */

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
    public ModelAndView handleNotFoundExceptions(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        List<String> exceptionMessages = new ArrayList<>();
        exceptionMessages.add(ex.getMessage());
        mav.addObject("exceptionMessages", exceptionMessages);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exceptionMessages", null);
        return mav;
    }
}
