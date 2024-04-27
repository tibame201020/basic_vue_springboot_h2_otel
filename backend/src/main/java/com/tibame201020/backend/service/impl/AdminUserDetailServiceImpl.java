package com.tibame201020.backend.service.impl;

import com.tibame201020.backend.model.AdminUser;
import com.tibame201020.backend.model.CustomUser;
import com.tibame201020.backend.model.UserRole;
import com.tibame201020.backend.model.security.Auth;
import com.tibame201020.backend.repo.AdminUserRepo;
import com.tibame201020.backend.repo.UserRoleRepo;
import com.tibame201020.backend.service.AdminUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserDetailServiceImpl implements AdminUserDetailService {
    private final AdminUserRepo adminUserRepo;
    private final UserRoleRepo userRoleRepo;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return Auth.builder()
                .email(adminUser.getEmail())
                .password(adminUser.getPassword())
                .active(adminUser.getActive())
                .roleList(userRoleRepo.findByEmail(email).map(UserRole::getRole).toList())
                .build();
    }
}
