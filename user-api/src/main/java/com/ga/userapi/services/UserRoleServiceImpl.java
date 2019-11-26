package com.ga.userapi.services;


import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    UserRoleRepository roleRepository;

    @Override
    public UserRole createRole(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public Iterable<UserRole> getRoleList() {
        return roleRepository.findAll();
    }
}
