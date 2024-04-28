package com.tibame201020.backend.model;

import com.tibame201020.backend.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * user roles
 */
@Data
@Entity
@Table(name = "user_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private Role role;
}
