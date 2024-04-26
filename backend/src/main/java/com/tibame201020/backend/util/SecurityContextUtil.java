package com.tibame201020.backend.util;


import com.tibame201020.backend.dto.CustomUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * global get spring security authentication
 * 可取得使用者參數在後端處理資料時期使用
 * from PreSecurityCheckJsonWebToken valid token: set to Authentication
 */
public class SecurityContextUtil {

    public static CustomUserDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Objects.isNull(authentication) ?
                null :
                (CustomUserDTO) authentication.getDetails();
    }
}
