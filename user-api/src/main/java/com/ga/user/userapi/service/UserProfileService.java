package com.ga.user.userapi.service;

import com.ga.user.userapi.model.UserProfile;

public interface UserProfileService {
    public UserProfile addUserProfile(String username, UserProfile newProfile);

    public UserProfile updateUserProfile(String username, UserProfile updateProfile);

    public UserProfile getUserProfile(String username);
}