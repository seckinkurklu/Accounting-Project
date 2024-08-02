package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);
    @Pointcut("execution(* com.cydeo.controller.DashboardController.getExchangeRates(*))")
    public void  getExchangeRatesPC() {}

    @Around(" getExchangeRatesPC()")
    public void beforeConsumingAPI(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis(); // Start time

        Object proceed = joinPoint.proceed(); // Proceed with the method execution

        long executionTime = System.currentTimeMillis() - start; // Calculate execution time

        // Log the method signature and execution time
        logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
    }
}
