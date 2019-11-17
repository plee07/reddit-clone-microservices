package com.ga.user.userapi.repository;

import com.ga.user.userapi.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
}