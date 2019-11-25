package com.ga.userapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.exception.IncorrectLoginException;
import com.ga.userapi.exception.UserAlreadyExistsException;
import com.ga.userapi.model.User;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserRepository;
import com.ga.userapi.repository.UserRoleRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        // check if user already exists
        if(userRepository.findByUsername(newUser.getUsername()) != null)
            throw new UserAlreadyExistsException("Username is taken!");
        else if (userRepository.findByEmail(newUser.getEmail()) != null)
            throw new UserAlreadyExistsException("Email is already taken!");

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