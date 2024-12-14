package com.project.retailhub.service.impelement;

import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.RoleRepository;
import com.project.retailhub.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleSvImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Role not found with id: " + id)
        );
    }
}
