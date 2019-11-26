package com.ga.user.userapi.controller;

import com.ga.user.userapi.config.JwtUtil;
import com.ga.user.userapi.model.JwtResponse;
import com.ga.user.userapi.model.User;
import com.ga.user.userapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class userController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/signup")
    @RestResource(path = "/signup")
    @ApiOperation("Allows you to signup. Create a account")
    public ResponseEntity<?> signup(@Valid @Param("user") @RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    @RestResource(path = "/login")
    @ApiOperation("Allows you to login into your account")
    public ResponseEntity<?> login(@Param("user")@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

}