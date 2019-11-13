package com.ga.user.userapi.service;

import com.ga.user.userapi.config.JwtUtil;
import com.ga.user.userapi.model.User;
import com.ga.user.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String signup(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        if(userRepository.save(newUser) != null){
            User userDetails = getUser(newUser.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }

    @Override
    public String login(User user) {
        User newUser = userRepository.findByUsername(user.getUsername());
        //userRepository.login(user.getUsername(), user.getPassword()) != null
        if(newUser != null && bCryptPasswordEncoder.matches(user.getPassword(), newUser.getPassword())){
            User userDetails = getUser(newUser.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }
}