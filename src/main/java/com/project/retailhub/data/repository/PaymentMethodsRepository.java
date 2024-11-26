package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodsRepository extends JpaRepository<PaymentMethod, String> {

    Optional<PaymentMethod> findByPaymentMethodId(String paymentMethodId);
    boolean existsByPaymentMethodId(String paymentMethodId);

}
