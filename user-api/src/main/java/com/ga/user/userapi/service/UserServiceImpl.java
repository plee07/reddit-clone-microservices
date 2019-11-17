package com.ga.user.userapi.service;

import com.ga.user.userapi.config.JwtUtil;
import com.ga.user.userapi.model.User;
import com.ga.user.userapi.model.UserRole;
import com.ga.user.userapi.repository.UserRepository;
import com.ga.user.userapi.repository.UserRoleRepository;
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
    private UserRoleRepository roleRepository;

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
        UserRole ur = roleRepository.findByName("ROLE_USER");
        if(ur == null){
            UserRole newRole = new UserRole();
            newRole.setName("ROLE_USER");
            ur = roleRepository.save(newRole);
        }
        newUser.addRole(ur);
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