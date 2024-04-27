package com.tibame201020.backend;

import com.tibame201020.backend.constant.CustomUserStatusEnum;
import com.tibame201020.backend.constant.Role;
import com.tibame201020.backend.model.CustomUser;
import com.tibame201020.backend.model.UserRole;
import com.tibame201020.backend.repo.CustomUserRepo;
import com.tibame201020.backend.repo.UserRoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Slf4j
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomUserRepo customUserRepo, UserRoleRepo userRoleRepo) {
        return args -> {

            CustomUser test = createCustomUser("test@123.cc");
            CustomUser publisher = createCustomUser("publisher@123.cc");
            CustomUser writer = createCustomUser("writer@123.cc");
            CustomUser reader = createCustomUser("reader@123.cc");
            CustomUser all = createCustomUser("all@123.cc");

            customUserRepo.save(test);
            customUserRepo.save(publisher);
            userRoleRepo.save(UserRole.builder().email(publisher.getEmail()).role(Role.PUBLISHER).build());
            customUserRepo.save(writer);
            userRoleRepo.save(UserRole.builder().email(writer.getEmail()).role(Role.WRITER).build());
            customUserRepo.save(reader);
            userRoleRepo.save(UserRole.builder().email(reader.getEmail()).role(Role.READER).build());
            customUserRepo.save(all);
            userRoleRepo.save(UserRole.builder().email(all.getEmail()).role(Role.PUBLISHER).build());
            userRoleRepo.save(UserRole.builder().email(all.getEmail()).role(Role.WRITER).build());
            userRoleRepo.save(UserRole.builder().email(all.getEmail()).role(Role.READER).build());
        };
    }

    private CustomUser createCustomUser(String email) {
        CustomUser customUser = new CustomUser();
        customUser.setEmail(email);
        customUser.setPassword(new BCryptPasswordEncoder().encode("123"));
        customUser.setActive(CustomUserStatusEnum.ACTIVE);

        return customUser;
    }

}
