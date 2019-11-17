package com.ga.user.userapi.controller;

import com.ga.user.userapi.model.UserRole;
import com.ga.user.userapi.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class UserRoleController {

    @Autowired
    private UserRoleService roleService;

    @PostMapping
    public UserRole createRole(@RequestBody UserRole role){
        return roleService.createRole(role);
    }

    @GetMapping("/list")
    public Iterable<UserRole> getRoleList(){
        return roleService.getRoleList();
    }



}
