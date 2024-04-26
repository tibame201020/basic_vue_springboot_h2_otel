package com.tibame201020.backend.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.tibame201020.backend.util.OpenTelemetryUtils;

@Component
@Aspect
public class LogAspect {

    @Before("execution(* org.slf4j.Logger.*(..))")
    private void beforeLogger() {
        OpenTelemetryUtils.addContextInfoToMDC();
    }

    @Around("execution(* com.tibame201020.backend.controller..*(..)) || execution(* com.tibame201020.backend.service..*(..))")
    private Object aroundControllerAndService(ProceedingJoinPoint joinPoint) throws Throwable {
        OpenTelemetryUtils.addInputEvent(joinPoint);
        Object result = joinPoint.proceed();
        OpenTelemetryUtils.addOutputEvent(joinPoint, result);
        return result;
    }
}