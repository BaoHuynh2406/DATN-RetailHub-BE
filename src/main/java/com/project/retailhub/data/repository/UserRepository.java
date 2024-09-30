package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Tìm tất cả user có isDelete = false (Chưa bị xóa)
    List<User> findAllByIsDeleteFalse();

    // Tìm tất cả user có isDelete = true (Đã bị xóa)
    List<User> findAllByIsDeleteTrue();

    // Tìm tất cả nhan vien có isDelete = false( chưa bị xóa) có tham số
    Page<User> findAllByIsDeleteFalse(Pageable pageable);

    // Tìm tất cả nhan vien có isDelete = true( đã bị xóa) có tham số
    Page<User> findAllByIsDeleteTrue(Pageable pageable);
}
