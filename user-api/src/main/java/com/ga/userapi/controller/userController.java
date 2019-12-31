package com.ga.userapi.controller;

import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.model.JwtResponse;
import com.ga.userapi.model.User;
import com.ga.userapi.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ApiOperation("User sign up")
    public ResponseEntity<?> signup(@Valid @RequestBody User user) throws Exception {
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    @ApiOperation("User login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }
}