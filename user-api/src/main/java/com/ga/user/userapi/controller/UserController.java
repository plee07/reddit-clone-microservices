package com.ga.user.userapi.controller;

import com.ga.user.userapi.config.JwtUtil;
import com.ga.user.userapi.model.JwtResponse;
import com.ga.user.userapi.model.User;
import com.ga.user.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/")
    public String home(){
        return "user home!";
    }

    @GetMapping("/hello")
    public String test(){
        return "Hello World!";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

}