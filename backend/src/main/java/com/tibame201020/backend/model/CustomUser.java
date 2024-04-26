package com.tibame201020.backend.model;

import com.tibame201020.backend.constant.CustomUserActiveEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Entity
@Table(name = "custom_user")
public class CustomUser implements Serializable {
    @Id
    @Column(name = "email")
    private String email;
    private String password;
    private CustomUserActiveEnum active;
}
