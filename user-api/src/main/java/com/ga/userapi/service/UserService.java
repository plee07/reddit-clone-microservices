package com.ga.userapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.userapi.model.User;

public interface UserService {
    public String signup(User user);
    public String login(User user);
    public String getUser(String message) throws JsonProcessingException;
}