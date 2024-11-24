package com.project.retailhub.config;

import com.project.retailhub.data.entity.*;
import com.project.retailhub.data.repository.*;
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
                                        TaxRepository taxRepository,
                                        CustomerRepository customerRepository,
                                        PaymentMethodsRepository paymentMethodsRepository) {
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

            if (customerRepository.findAll().isEmpty()) {
                customerRepository.save(
                        Customer.builder()
                                .fullName("KHACH LE")
                                .phoneNumber("0000000000")
                                .isActive(true)
                                .points(0)
                                .isDelete(false)
                                .build()
                );
                log.info("Create customer default...OK");
            }

            if (categoryRepository.findAll().isEmpty()) {
                categoryRepository.save(
                        Category.builder()
                                .categoryName("Chưa phân loại")
                                .isDelete(false)
                                .build()
                );
                log.info("Create caterory default...OK");
            }

            if (taxRepository.findAll().isEmpty()) {
                taxRepository.save(
                        Tax.builder()
                                .taxId("NO-THUE")
                                .taxName("NO-THUE")
                                .taxRate(0.0)
                                .isDelete(false)
                                .build()
                );
            }

            if (paymentMethodsRepository.findAll().isEmpty()) {
                paymentMethodsRepository.save(
                        PaymentMethod.builder()
                                .paymentMethodId("CASH")
                                .paymentName("Tiền mặt")
                                .build()
                );
                paymentMethodsRepository.save(
                        PaymentMethod.builder()
                                .paymentMethodId("BANKING")
                                .paymentName("Chuyển khoản")
                                .build()
                );

            }


        };
    }

}
