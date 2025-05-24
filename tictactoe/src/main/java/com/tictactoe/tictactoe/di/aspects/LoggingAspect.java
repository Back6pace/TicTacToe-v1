package com.tictactoe.tictactoe.di.aspects;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private final static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Method " + methodName +
                " with parameters " + Arrays.asList(args) +
                " will execute");

        Object result = joinPoint.proceed();

        logger.info("Method " + methodName + " executed with result: " + result);
        return result;
    }

    static {
        try {
            FileHandler file = new FileHandler("game_log.log", true);
            file.setLevel(Level.ALL);
            file.setFormatter(new SimpleFormatter());

            logger.addHandler(file);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException ex) {
            logger.log(Level.SEVERE, "File error", ex);
        }
    }
}