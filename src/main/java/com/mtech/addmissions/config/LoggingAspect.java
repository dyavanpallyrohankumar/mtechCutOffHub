package com.mtech.addmissions.config;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.mtech.addmissions.service.implementation.*.*(..))")
    public void beforeExecution(JoinPoint joinpoint) {
        System.out.println(joinpoint.getSignature() + " is Started Execution at " + LocalDateTime.now());
    }

    @After("execution(* com.mtech.addmissions.service.implementation.*.*(..))")
    public void afterExecution(JoinPoint joinpoint) {
        System.out.println(joinpoint.getSignature() + " is Completed Execution at " + LocalDateTime.now());
    }

    @AfterThrowing(pointcut = "execution(* com.mtech.addmissions.service.implementation.*.*(..))", throwing = "e")
    public void afterThrowingException(JoinPoint joinpoint, Exception e) {
        System.err.println(joinpoint.getSignature() + " is Thrown an Exception at " + LocalDateTime.now() + " Error: "
                + e.getMessage());
    }
}
