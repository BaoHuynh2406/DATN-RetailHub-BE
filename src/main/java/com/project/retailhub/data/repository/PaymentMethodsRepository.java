package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodsRepository extends JpaRepository<PaymentMethod, Integer> {

    Optional<PaymentMethod> findByPaymentMethodId(int paymentMethodId);
    boolean existsByPaymentMethodId(int paymentMethodId);

}
