package com.project.retailhub.config;

import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.CategoryRepository;
import com.project.retailhub.data.repository.TaxRepository;
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
    static final String ADMIN_EMAIL = "admin@123";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        CategoryRepository categoryRepository,
                                        TaxRepository taxRepository) {
        log.info("Initializing application.....");
        return args ->
        {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
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
                                .roleId(roleRepository.findById("ADMIN").get().getRoleId())
                                .fullName("Quản lý")
                                .address("TP Hồ Chí Minh")
                                .phoneNumber("123456789")
                                .startDate(Date.valueOf(LocalDate.now()))
                                .isActive(true)
                                .isDelete(false)
                                .build()
                        );

                log.info("Application initialization completed ...OK");
            }

            if(roleRepository.findAll().isEmpty()) {
                categoryRepository.save(
                        Category.builder()
                                .categoryName("Loai hang 1")
                                .isDelete(false)
                                .build()
                );
                log.info("Create caterory default...OK");
            }

            if(taxRepository.findAll().isEmpty()){
                taxRepository.save(
                        Tax.builder()
                                .taxId("THUE")
                                .taxName("THUE")
                                .taxRate(0.1)
                                .isDelete(false)
                                .build()
                );
            }

        };
    }

}
