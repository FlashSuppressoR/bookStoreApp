package com.flashsuppressor.java.lab.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String EXC_MESSAGE = "Catch exception in method: %s with message: %s";
//    protected final Logger logger;
//
//    @Autowired
//    public ExceptionAdvice(Logger logger) {
//        this.logger = logger;
//    }

    @AfterThrowing(pointcut = "execution(* com.flashsuppressor.java.lab.repository.impl.*.*(..))", throwing = "ex")
    public void errorLogging(JoinPoint joinpoint, Exception ex){

        logger.error("\nLOGGING START\n" + (String.format(EXC_MESSAGE, joinpoint.getSignature().getName(), ex.getMessage()) + "\nLOGGING END\n"));
        logger.error(Arrays.toString(ex.getStackTrace()));
    }
}
