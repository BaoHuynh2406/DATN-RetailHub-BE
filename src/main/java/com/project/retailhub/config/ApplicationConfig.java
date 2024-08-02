package com.project.retailhub.config;

import com.project.retailhub.data.entity.Employee;
import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.EmployeeRepository;
import com.project.retailhub.data.repository.RoleRepository;
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
    ApplicationRunner applicationRunner(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args ->
        {
            if (!employeeRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                return;
            }

            log.info("Create Account Default: {} | {}", ADMIN_EMAIL, ADMIN_PASSWORD);
            if (roleRepository.findById("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder()
                        .roleId("ADMIN")
                        .roleDescription("Quản lý")
                        .build());
            }

            employeeRepository.save
                    (Employee.builder()
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .role(roleRepository.findById("ADMIN").get())
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
