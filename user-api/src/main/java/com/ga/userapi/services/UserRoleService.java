package com.ga.userapi.services;

import com.ga.userapi.model.UserRole;

public interface UserRoleService {

    public UserRole createRole(UserRole role);
    public Iterable<UserRole> getRoleList();
}
