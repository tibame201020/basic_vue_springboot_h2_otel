package com.tibame201020.backend.service;

import com.tibame201020.backend.dto.CustomUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserDetailService extends UserDetailsService {

    List<CustomUserDTO> fetchAllUser();

}
