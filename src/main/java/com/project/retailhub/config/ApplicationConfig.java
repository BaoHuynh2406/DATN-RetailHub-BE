package com.project.retailhub.config;

import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.entity.Roles;
import com.project.retailhub.data.repository.EmployeesRepository;
import com.project.retailhub.data.repository.RolesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@retailhub.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(EmployeesRepository employeesRepository, RolesRepository rolesRepository) {
        log.info("Initializing application.....");
        return args ->
        {
            if (!employeesRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                return;
            }

            log.info("Create Account Default: {} | {}", ADMIN_EMAIL, ADMIN_PASSWORD);
            if (rolesRepository.findById("ADMIN").isEmpty()) {
                rolesRepository.save(Roles.builder()
                        .roleId("ADMIN")
                        .roleDescription("Quản lý")
                        .build());
            }

            employeesRepository.save
                    (Employees.builder()
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .role(rolesRepository.findById("ADMIN").get())
                    .fullName("Quản lý")
                    .address("TP Hồ Chí Minh")
                    .phoneNumber("00000000")
                    .startDate(Date.valueOf(LocalDate.now()))
                    .status(true)
                    .build()
                    );
            log.info("Application initialization completed .....");
        };
    }

}
