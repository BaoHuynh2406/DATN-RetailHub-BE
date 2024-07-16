package com.mts.minimart.data.repository;

import com.mts.minimart.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>
{
    //Các phương thức thêm xóa sửa tìm kiếm đã được tạo mặc định khi kế thừa lớp JPA repository

    List<Users> findByRoleIdRole(String roleId);

}
