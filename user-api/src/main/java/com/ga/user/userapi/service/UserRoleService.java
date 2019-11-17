package com.ga.user.userapi.service;

import com.ga.user.userapi.model.User;
import com.ga.user.userapi.model.UserRole;

public interface UserRoleService {

    public UserRole createRole(UserRole role);
    public Iterable<UserRole> getRoleList();
}
