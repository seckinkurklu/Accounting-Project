package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

   //private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

//    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")
//    public void executionTimeAnnotationPC() {}

    @Around("@annotation(com.cydeo.annotation.ExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = joinPoint.proceed();
//        long executionTime = System.currentTimeMillis() - start;
//        String methodName = joinPoint.getSignature().getName();
//        String className = joinPoint.getSignature().getDeclaringTypeName();
//        log.info("Execution of {}#{} took {} ms", className, methodName, executionTime);
//        return proceed;
        long beforeTime = System.currentTimeMillis();
        Object result = null;
        log.info("Execution starts:");

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long afterTime = System.currentTimeMillis();

        log.info("Time taken to execute: {} ms - Method: {}"
                , (afterTime - beforeTime), joinPoint.getSignature().toShortString());
        return result;
    }




    @AfterThrowing(pointcut = "@annotation(com.cydeo.annotation.ExecutionTime)" ,throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {}() - Exception: {} - Message: {}",
                joinPoint.getSignature().getName(),
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }
}
