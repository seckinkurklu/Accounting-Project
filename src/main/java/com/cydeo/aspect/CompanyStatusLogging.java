package com.cydeo.aspect;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CompanyStatusLogging {
    Logger logger= LoggerFactory.getLogger(CompanyStatusLogging.class);
    private final UserService userService;;

    public CompanyStatusLogging(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("execution(* com.cydeo.service.CompanyService.activateCompany(..))")
    public void activatedMethod(){}
    @Pointcut("execution(* com.cydeo.service.CompanyService.deactivateCompany(..))")
    public void deactivatedMethod(){}

    @AfterReturning( "activatedMethod()")
    public void logAfterActivation() {

        logCompanyStatusChange("activate");
    }

    @AfterReturning("deactivatedMethod()")
    public void logAfterDeactivation() {

        logCompanyStatusChange("deactivate");
    }

    private void logCompanyStatusChange(String methodName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto user = userService.findByUsername(username);
//        logger.info(String.format("Method: %s,CompanyName:, User: %s %s (%s)",
        logger.info("Method:{},CompanyName:{},User:{} {} {}",
                methodName, user.getCompany().getTitle(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername());
    }

}
