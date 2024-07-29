package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
    @Query("SELECT * FROM employees WHERE email = :email")
    Employees getByEmail(String email);
}
