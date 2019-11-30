package com.ga.userapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.userapi.exception.IncorrectLoginException;
import com.ga.userapi.exception.UserAlreadyExistsException;
import com.ga.userapi.model.User;

public interface UserService {
    public String signup(User user)throws UserAlreadyExistsException;
    public String login(User user)throws IncorrectLoginException;
    public String getUser(String message) throws JsonProcessingException;
}