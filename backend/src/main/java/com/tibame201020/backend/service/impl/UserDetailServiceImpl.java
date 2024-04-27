package com.tibame201020.backend.service.impl;

import com.tibame201020.backend.model.CustomUser;
import com.tibame201020.backend.model.UserRole;
import com.tibame201020.backend.model.security.Auth;
import com.tibame201020.backend.repo.CustomUserRepo;
import com.tibame201020.backend.repo.UserRoleRepo;
import com.tibame201020.backend.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作為實作UserDetailsService
 * Override loadUserByUsername 提供spring security驗證
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final CustomUserRepo customUserRepo;
    private final UserRoleRepo userRoleRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return Auth.builder()
                .customUser(customUser)
                .roleList(userRoleRepo.findByEmail(email).map(UserRole::getRole).toList())
                .build();
    }
}
