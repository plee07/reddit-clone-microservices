package com.ga.userapi.controller;

import com.ga.userapi.model.UserRole;
import com.ga.userapi.services.UserRoleService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Get a list of all User Roles")
    public Iterable<UserRole> getRoleList(){
        return roleService.getRoleList();
    }
}
