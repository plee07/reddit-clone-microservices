package com.ga.userapi.repository;

import com.ga.userapi.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
}