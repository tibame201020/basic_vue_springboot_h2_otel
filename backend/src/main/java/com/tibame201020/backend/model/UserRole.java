package com.tibame201020.backend.model;

import com.tibame201020.backend.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private RoleEnum role;
}
