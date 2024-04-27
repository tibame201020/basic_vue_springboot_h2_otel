package com.tibame201020.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomException extends RuntimeException {
    private int code;
    private String message;
    private String traceId;
}
