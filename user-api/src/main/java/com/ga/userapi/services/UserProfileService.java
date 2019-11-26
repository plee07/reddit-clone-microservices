package com.ga.userapi.services;

import com.ga.userapi.model.UserProfile;

public interface UserProfileService {
    public UserProfile addUserProfile(String username, UserProfile newProfile);

    public UserProfile updateUserProfile(String username, UserProfile updateProfile);

    public UserProfile getUserProfile(String username);
}