package com.ga.user.userapi.service;


import com.ga.user.userapi.model.User;
import com.ga.user.userapi.model.UserRole;
import com.ga.user.userapi.repository.UserRoleRepository;
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
