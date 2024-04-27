package com.tibame201020.backend.model.security;

import com.tibame201020.backend.constant.CustomUserStatusEnum;
import com.tibame201020.backend.constant.Role;
import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.model.CustomUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * implement UserDetails
 * 對應spring security驗證完資料庫, 將自定義user轉成UserDetails
 * 便於驗證帳戶狀態
 * getAuthorities產roles, 需要主動添加ROLE_作為prefix
 */
@Data
@Builder
public class Auth implements UserDetails {
    private String email;
    private String password;
    private CustomUserStatusEnum active;
    private List<Role> roleList;

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return roleList
                .stream()
                .map(role -> SystemProps.ROLE_PREFIX + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return CustomUserStatusEnum.ACTIVE.equals(active);
    }
}
