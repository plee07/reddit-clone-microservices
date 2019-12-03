package com.ga.userapi.services;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Override
    public String signup(User newUser) throws UserAlreadyExistsException{
        // check if user already exists
        if(userRepository.findByUsername(newUser.getUsername()) != null) {
            logger.error("Username already taken");
            throw new UserAlreadyExistsException("Username is taken!");
        }
        else if (userRepository.findByEmail(newUser.getEmail()) != null) {
            logger.error("Email already taken");
            throw new UserAlreadyExistsException("Email is already taken!");
        }

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        UserRole ur = roleRepository.findByName("ROLE_USER");
        if(ur == null){
            UserRole newRole = new UserRole();
            newRole.setName("ROLE_USER");
            ur = roleRepository.save(newRole);
        }
        newUser.addRole(ur);
        userRepository.save(newUser);
        return jwtUtil.generateToken(newUser);

    }

    @Override
    public String login(User user) throws IncorrectLoginException{
        User loggedInUser;

        // allows user to login through either username or email
        if(user.getUsername() != null){
            loggedInUser = userRepository.findByUsername(user.getUsername());
        } else{
            loggedInUser = userRepository.findByEmail(user.getEmail());
        }

        if(loggedInUser != null && bCryptPasswordEncoder.matches(user.getPassword(), loggedInUser.getPassword())){
            return jwtUtil.generateToken(loggedInUser);
        }
        else {
            logger.error("Incorrect Login Credentials: LOG!");
            throw new IncorrectLoginException("Incorrect Login Credentials");
        }
    }

    @Override
    @RabbitListener(queuesToDeclare = @Queue("getUserById"))
    public String getUser(String message) throws JsonProcessingException {
        ObjectMapper json = new ObjectMapper();
        Long userId = Long.parseLong(message);
        User user = userRepository.findUserByUserId(userId);
        String userJson = json.writeValueAsString(user);
        return userJson;
    }

}