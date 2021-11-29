package com.flashsuppressor.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ExceptionAdvice {
    private static final String EXC_MESSAGE = "Catch exception in method: %s with message: %s";

    @AfterThrowing(pointcut = "execution(* com.flashsuppressor.java.lab.repository.hibernate.impl.*.*(..))",
            throwing = "ex")
    public void errorLogging(JoinPoint joinpoint, Exception ex) {
        log.error("\nLOGGING START\n" + (String.format(EXC_MESSAGE,
                joinpoint.getSignature().getName(), ex.getMessage()) + "\nLOGGING END\n"));
        log.error(Arrays.toString(ex.getStackTrace()));
    }
}
