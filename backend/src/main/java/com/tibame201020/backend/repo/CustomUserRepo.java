package com.tibame201020.backend.repo;


import com.tibame201020.backend.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepo extends JpaRepository<CustomUser, String> {
}
