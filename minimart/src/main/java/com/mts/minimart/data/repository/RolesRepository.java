package com.mts.minimart.data.repository;

import com.mts.minimart.data.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String>
{
    //Các phương thức thêm xóa sửa tìm kiếm đã được tạo mặc định khi kế thừa lớp JPA repository
}
