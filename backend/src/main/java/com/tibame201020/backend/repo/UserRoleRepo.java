package com.tibame201020.backend.repo;


import com.tibame201020.backend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

    Stream<UserRole> findByEmail(String email);
}
