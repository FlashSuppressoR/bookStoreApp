package com.flashsuppressor.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FindAdvice {
    private static final String FIND_METHOD_MESSAGE = "Method: %s has been called";
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Logger logger;

    @Autowired
    public FindAdvice(Logger logger) {
        this.logger = logger;
    }

    @Pointcut("execution(* com.flashsuppressor.java.lab.repository.impl.*.find*(..))")
    public void findMethodsInRepositories() {
    }

    @AfterReturning(value = "findMethodsInRepositories()")
    public void printSuccessfulExecutionResult(JoinPoint joinPoint) {
        logger.info("\nLOGGING START\n" + String.format(FIND_METHOD_MESSAGE, joinPoint.getSignature().getName()) + "\nLOGGING END\n");
    }
}
