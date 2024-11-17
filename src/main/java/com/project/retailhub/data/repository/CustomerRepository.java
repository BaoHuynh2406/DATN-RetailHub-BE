package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    // Tìm tất cả khách hàng có isDelete là false (khách hàng đang hoạt động)
    List<Customer> findAllByIsDeleteFalse();

    // Tìm tất cả khách hàng có isDelete là true (khách hàng đã bị xóa)
    List<Customer> findAllByIsDeleteTrue();
    //Tìm kiếm bằng số điện thoại
    boolean existsByPhoneNumber(String phoneNumber);
}
