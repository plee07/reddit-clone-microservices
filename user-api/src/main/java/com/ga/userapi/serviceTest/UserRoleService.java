package com.ga.userapi.serviceTest;

import com.ga.userapi.model.UserRole;

public interface UserRoleService {

    public UserRole createRole(UserRole role);
    public Iterable<UserRole> getRoleList();
}
