package com.tibame201020.backend.util;

import io.micrometer.core.instrument.util.IOUtils;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * handle add event to tracer
 */
public class OpenTelemetryUtil {
    private static final String TRACE_ID = "traceId";
    private static final String EVENT_PATTERN = "%s %s";
    private static final String CURRENT_USER = "currentUser";

    public static void addInputEvent(ProceedingJoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        addContextInfoToMDC();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName() + "()";
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(Span.current())) {
            Span.current().addEvent(String.format(EVENT_PATTERN, className, methodName),
                    Attributes.of(
                            AttributeKey.stringKey("args"), Arrays.toString(args),
                            AttributeKey.stringKey("type"), "input",
                            AttributeKey.stringKey("class"), className,
                            AttributeKey.stringKey("method"), methodName,
                            AttributeKey.stringKey("currentUser"), Objects.isNull(SecurityContextUtil.getUserInfo()) ? "" : SecurityContextUtil.getUserInfo().toString()
                    )
            );
        }
        log.info("== START {}.{}", joinPoint.getTarget().getClass().getSimpleName(), methodName);
        log.info("{} input: {}", methodName, Arrays.toString(args));
    }

    public static void addOutputEvent(ProceedingJoinPoint joinPoint, Object result) {
        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        addContextInfoToMDC();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName() + "()";

        if (Objects.nonNull(result) && Objects.nonNull(Span.current())) {
            Span.current().addEvent(String.format(EVENT_PATTERN, className, methodName),
                    Attributes.of(
                            AttributeKey.stringKey("result"), Objects.toString(result),
                            AttributeKey.stringKey("type"), "output",
                            AttributeKey.stringKey("class"), className,
                            AttributeKey.stringKey("method"), methodName,
                            AttributeKey.stringKey("currentUser"), Objects.isNull(SecurityContextUtil.getUserInfo()) ? "" : SecurityContextUtil.getUserInfo().toString()
                    )
            );
        }
        if (Objects.nonNull(result)) {
            log.info("{} return: {}", methodName, result);
        }
    }

    public static void addExceptionEvent(HttpServletRequest request, Exception exception) {
        addContextInfoToMDC();

        if (Objects.nonNull(Span.current())) {
            Span.current().recordException(exception,
                    Attributes.of(
                            AttributeKey.stringKey("request.path"), request.getRequestURI(),
                            AttributeKey.stringKey("request.method"), request.getMethod(),
                            AttributeKey.stringKey("request.headers"), getRequestHeaders(request),
                            AttributeKey.stringKey("request.parameters"), getRequestParameters(request),
                            AttributeKey.stringKey("request.body"), getRequestBody(request),
                            AttributeKey.stringKey("exception.message"), exception.getMessage()
                    )
            );
            Span.current().setStatus(StatusCode.ERROR);
        }
    }

    /**
     * explore request header
     */
    private static String getRequestHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n"));

        return headers.toString();
    }

    /**
     * explore request parameters
     */
    private static String getRequestParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder();
        request.getParameterNames().asIterator().forEachRemaining(parameterName ->
                parameters.append(parameterName).append(": ").append(request.getParameter(parameterName)).append("\n"));

        return parameters.toString();
    }

    /**
     * explore request body
     */
    private static String getRequestBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * add context-info to context
     */
    public static void addContextInfoToMDC() {
        MDC.put(CURRENT_USER, SecurityContextUtil.getCurrentUserEmail());
        MDC.put(TRACE_ID, getTraceId());
    }

    /**
     * get traceId for tracing log
     */
    public static String getTraceId() {
        SpanContext spanContext = Span.current().getSpanContext();
        if (spanContext.isValid()) {
            return spanContext.getTraceId();
        }
        return "";
    }
}
