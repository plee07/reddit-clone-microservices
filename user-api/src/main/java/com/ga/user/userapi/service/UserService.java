package com.ga.user.userapi.service;

import com.ga.user.userapi.model.User;

public interface UserService {
    public String signup(User user);
    public String login(User user) ;
    public User getUser(String username);
}