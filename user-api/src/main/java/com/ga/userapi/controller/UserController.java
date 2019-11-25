package com.ga.userapi.controller;

import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.model.JwtResponse;
import com.ga.userapi.model.User;
import com.ga.userapi.serviceTest.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

}