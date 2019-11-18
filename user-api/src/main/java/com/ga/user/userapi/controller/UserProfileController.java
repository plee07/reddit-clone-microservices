package com.ga.user.userapi.controller;


import com.ga.user.userapi.model.UserProfile;
import com.ga.user.userapi.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserProfileController {


    private UserProfileService userProfileService;

    @Autowired
    public void setUserProfileService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public UserProfile getUserProfile(@RequestHeader("username") String username) {
        return userProfileService.getUserProfile(username);
    }

    @PostMapping
    public UserProfile addUserProfile(@RequestHeader("username") String username, @RequestBody UserProfile userProfile) {
        return userProfileService.addUserProfile(username, userProfile);
    }

    @PatchMapping
    public UserProfile updateUserProfile(@RequestHeader("username") String username, @RequestBody UserProfile updateProfile) {
        return userProfileService.updateUserProfile(username, updateProfile);
    }

}