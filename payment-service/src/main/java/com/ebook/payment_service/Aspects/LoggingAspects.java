package com.ebook.payment_service.Aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspects {

    private final Logger logger = Logger.getLogger(LoggingAspects.class.getName());

    @Around("execution(* com.ebook.payment_service.Service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("Entering method: " + methodName + " with arguments: " + Arrays.toString(methodArgs));

        Object result = joinPoint.proceed();

        logger.info("Exiting method: " + methodName + " with result: " + result);

        return result;

    }
}
