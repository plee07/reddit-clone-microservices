package com.example.apigateway.service;

import com.example.apigateway.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User getUser(String username);
}