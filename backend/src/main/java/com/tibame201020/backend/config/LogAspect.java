package com.tibame201020.backend.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.tibame201020.backend.util.OpenTelemetryUtil;

/**
 * aop config
 */
@Component
@Aspect
public class LogAspect {

    @Around("execution(* com.tibame201020.backend.controller..*(..)) || execution(* com.tibame201020.backend.service..*(..))")
    private Object aroundControllerAndService(ProceedingJoinPoint joinPoint) throws Throwable {
        OpenTelemetryUtil.addInputEvent(joinPoint);
        Object result = joinPoint.proceed();
        OpenTelemetryUtil.addOutputEvent(joinPoint, result);
        return result;
    }
}