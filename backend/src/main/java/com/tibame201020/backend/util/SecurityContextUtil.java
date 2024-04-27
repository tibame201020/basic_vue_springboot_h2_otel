package com.tibame201020.backend.util;


import com.tibame201020.backend.dto.CustomUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * global get spring security authentication
 * 可取得使用者參數在後端處理資料時期使用
 * from PreSecurityCheckJsonWebToken valid token: set to Authentication
 */
@Slf4j
public class SecurityContextUtil {

    public static CustomUserDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) ?
                null :
                (CustomUserDTO) authentication.getDetails();
    }

    public static String getCurrentUserEmail() {
        return Objects.isNull(getUserInfo()) ? "" : getUserInfo().getEmail();
    }
}
