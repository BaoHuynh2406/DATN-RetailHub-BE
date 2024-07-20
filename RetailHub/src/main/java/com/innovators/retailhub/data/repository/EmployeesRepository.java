package com.innovators.retailhub.data.repository;

import com.innovators.retailhub.data.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

}
