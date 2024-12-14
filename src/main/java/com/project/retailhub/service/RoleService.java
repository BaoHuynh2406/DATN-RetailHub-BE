package com.project.retailhub.service;

import com.project.retailhub.data.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Role getById(String id);
}
