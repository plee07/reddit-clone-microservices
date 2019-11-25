package com.ga.user.userapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.user.userapi.config.JwtUtil;
import com.ga.user.userapi.exception.IncorrectLoginException;
import com.ga.user.userapi.model.User;
import com.ga.user.userapi.model.UserRole;
import com.ga.user.userapi.repository.UserRepository;
import com.ga.user.userapi.repository.UserRoleRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    private ObjectMapper json = new ObjectMapper();

    @Override
    public String signup(User newUser){
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        UserRole ur = roleRepository.findByName("ROLE_USER");
        if(ur == null){
            UserRole newRole = new UserRole();
            newRole.setName("ROLE_USER");
            ur = roleRepository.save(newRole);
        }
        newUser.addRole(ur);
        if(userRepository.save(newUser) != null){
            return jwtUtil.generateToken(newUser);
        }
        return null;
    }

    @Override
    public String login(User user){
        User loggedInUser;
        if(user.getUsername() != null){
            loggedInUser = userRepository.findByUsername(user.getUsername());
        } else{
            loggedInUser = userRepository.findByEmail(user.getEmail());
        }
        if(loggedInUser != null && bCryptPasswordEncoder.matches(user.getPassword(), loggedInUser.getPassword())){
            return jwtUtil.generateToken(loggedInUser);
        }
        else {
            throw new IncorrectLoginException("Incorrect Login Credentials");
        }
    }

    @Override
    @RabbitListener(queuesToDeclare = @Queue("getUserById"))
    public String getUser(String message) throws JsonProcessingException {
        Long userId = Long.parseLong(message);
        User user = userRepository.findById(userId).get();
        String userJson = json.writeValueAsString(user);
        return userJson;
    }

}