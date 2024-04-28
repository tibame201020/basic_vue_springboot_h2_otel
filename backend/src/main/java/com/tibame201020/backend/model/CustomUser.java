package com.tibame201020.backend.model;

import com.tibame201020.backend.constant.CustomUserStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * front-office user
 */
@Data
@Entity
@Table(name = "custom_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser implements Serializable {
    @Id
    @Column(name = "email")
    private String email;
    private String password;
    private CustomUserStatusEnum active;
}
