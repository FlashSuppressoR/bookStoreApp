package com.flashsuppressor.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FindAdvice {
    private static final String FIND_METHOD_MESSAGE = "Method: %s has been called";

    @Pointcut("execution(* com.flashsuppressor.java.lab.service.impl.*.find*(..))")
    public void findMethodsInRepositories() {
    }

    @AfterReturning(value = "findMethodsInRepositories()")
    public void printSuccessfulExecutionResult(JoinPoint joinPoint) {
        log.info("\nLOGGING START\n" + String.format(FIND_METHOD_MESSAGE, joinPoint.getSignature().getName()) + "\nLOGGING END\n");
    }
}
