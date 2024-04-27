package com.tibame201020.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * request record
 */
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    private String requestPath;
    private String userEmail;

    private String traceId;
    private LocalDateTime recordDateTime;
    private Boolean isError;
    private int responseStatus;
}
