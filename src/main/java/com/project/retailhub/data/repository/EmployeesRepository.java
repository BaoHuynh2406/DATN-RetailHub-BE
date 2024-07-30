package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    Optional<Employees> findByEmail(String email);
}
