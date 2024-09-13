package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    List<Customer> findByIsActiveTrue();

    List<Customer> findByIsDeleteTrue();
}
