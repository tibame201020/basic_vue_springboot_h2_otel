package com.tibame201020.backend.repo;

import com.tibame201020.backend.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepo extends JpaRepository<AdminUser, String> {
}
