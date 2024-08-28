package com.project.retailhub.config;

import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.data.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationConfig {

    @NonFinal
    static final String ADMIN_EMAIL = "admin@retailhub.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args ->
        {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if (!userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                return;
            }

            log.info("Create Account Default: {} | {}", ADMIN_EMAIL, ADMIN_PASSWORD);
            if (roleRepository.findById("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder()
                        .roleId("ADMIN")
                        .roleDescription("Quản lý")
                        .build());
            }

            userRepository.save
                    (User.builder()
                            .email(ADMIN_EMAIL)
                            .password(passwordEncoder.encode(ADMIN_PASSWORD))
                            .role(roleRepository.findById("ADMIN").get())
                            .fullName("Quản lý")
                            .address("TP Hồ Chí Minh")
                            .phoneNumber("123456789")
                            .startDate(Date.valueOf(LocalDate.now()))
                            .isActive(true)
                            .isDelete(false)
                            .build()
                    );
            log.info("Application initialization completed .....");
        };
    }

}
