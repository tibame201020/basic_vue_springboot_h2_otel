package com.tibame201020.backend.service.impl;

import com.tibame201020.backend.dto.CustomException;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.model.CustomUser;
import com.tibame201020.backend.model.UserRole;
import com.tibame201020.backend.model.security.Auth;
import com.tibame201020.backend.repo.CustomUserRepo;
import com.tibame201020.backend.repo.UserRoleRepo;
import com.tibame201020.backend.service.UserDetailService;
import com.tibame201020.backend.util.PatternValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作為實作UserDetailsService
 * Override loadUserByUsername 提供spring security驗證
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final CustomUserRepo customUserRepo;
    private final UserRoleRepo userRoleRepo;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!PatternValidUtils.isEmailLegal(email)) {
            throw CustomException.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("email is un-valid")
                    .build();
        }
        CustomUser customUser = customUserRepo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return Auth.builder()
                .email(customUser.getEmail())
                .password(customUser.getPassword())
                .active(customUser.getActive())
                .roleList(userRoleRepo.findByEmail(email).map(UserRole::getRole).toList())
                .build();
    }

    @Transactional
    @Override
    public List<CustomUserDTO> fetchAllUser() {
        return customUserRepo.findAll().stream()
                .map(customUser -> CustomUserDTO.builder()
                                .email(customUser.getEmail())
                                .active(customUser.getActive())
                                .roleList(userRoleRepo.findByEmail(customUser.getEmail()).map(UserRole::getRole).toList())
                                .build())
                .toList();
    }
}
