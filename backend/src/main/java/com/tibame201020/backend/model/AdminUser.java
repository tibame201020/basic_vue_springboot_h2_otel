package com.tibame201020.backend.model;

import com.tibame201020.backend.constant.CustomUserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "admin_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser implements Serializable {
    @Id
    @Column(name = "email")
    private String email;
    private String password;
    private CustomUserStatusEnum active;
}
