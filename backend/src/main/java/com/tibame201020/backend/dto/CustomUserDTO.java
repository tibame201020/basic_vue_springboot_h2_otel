package com.tibame201020.backend.dto;

import com.tibame201020.backend.constant.CustomUserStatusEnum;
import com.tibame201020.backend.constant.Role;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class CustomUserDTO implements Serializable {
    private String email;
    private CustomUserStatusEnum active;
    private List<Role> roleList;
}
