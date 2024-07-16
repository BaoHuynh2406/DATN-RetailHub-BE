package com.mts.minimart.service.impelement;

import com.mts.minimart.data.dto.UsersDto;
import com.mts.minimart.data.entity.Users;
import com.mts.minimart.data.repository.UsersRepository;
import com.mts.minimart.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServiceImpl implements UsersService
{

    @Autowired
    UsersRepository usersRepository;


    @Override
    public void saveUser(Users user) {
        usersRepository.save(user);
    }

    @Override
    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    @Override
    public List<UsersDto> findAllUsers() {
       return UsersDto.convertListUsersEtoListUsersDto(usersRepository.findAll());
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public List<UsersDto> getUsersByRoleId(String roleId) {
        return UsersDto.convertListUsersEtoListUsersDto(usersRepository.findByRoleIdRole(roleId));
    }

    @Override
    public int demSoLuongNguoiDung() {
        int dem = usersRepository.findAll().size();
        return dem;
    }
}
