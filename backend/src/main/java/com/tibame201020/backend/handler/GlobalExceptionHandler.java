package com.tibame201020.backend.handler;

import com.tibame201020.backend.dto.CustomException;
import com.tibame201020.backend.util.OpenTelemetryUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handle
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomException handleException(HttpServletRequest request, Exception e) {
        log.error("global error: {} ", e.getMessage());
        log.error("error class: {} ", e.getClass());
        OpenTelemetryUtil.addExceptionEvent(request, e);

        if (e instanceof CustomException customException) {
            return CustomException.builder()
                    .code(customException.getCode())
                    .message(customException.getMessage())
                    .traceId(OpenTelemetryUtil.getTraceId())
                    .build();
        }

        return CustomException
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .traceId(OpenTelemetryUtil.getTraceId())
                .build();
    }
}
