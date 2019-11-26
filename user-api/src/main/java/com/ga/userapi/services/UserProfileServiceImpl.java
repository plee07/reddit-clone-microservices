package com.ga.userapi.services;
import com.ga.userapi.model.User;
import com.ga.userapi.model.UserProfile;
import com.ga.userapi.repository.UserProfileRepository;
import com.ga.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserProfileServiceImpl implements UserProfileService {


    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserProfile addUserProfile(String username, UserProfile newProfile) {
        User user = userRepository.findByUsername(username);
        user.setUserProfile(newProfile);
        return userProfileRepository.save(newProfile);

    }

    @Override
    public UserProfile getUserProfile(String username) {
        Long profileId = getProfileIdByUsername(username);
        return (profileId != -1L) ? userProfileRepository.findUserProfileByProfileId(profileId) : null;
    }

    @Override
    public UserProfile updateUserProfile(String username, UserProfile updateProfile) {
        Long profileId = getProfileIdByUsername(username);
        System.out.println(profileId);
        if(profileId != -1L){
            UserProfile up = userProfileRepository.findUserProfileByProfileId(profileId);
            if(updateProfile.getAddress() != null) up.setAddress(updateProfile.getAddress());
            if(updateProfile.getEmail() != null) up.setEmail(updateProfile.getEmail());
            if(updateProfile.getMobile() != null) up.setMobile(updateProfile.getMobile());
            return userProfileRepository.save(up);
        }
        return null;
    }

    // helper method to get profile id
    public Long getProfileIdByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user != null) return user.getUserProfile().getProfileId();
        else return -1L;
    }

}