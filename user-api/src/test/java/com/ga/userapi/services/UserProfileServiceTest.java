package com.ga.userapi.services;

import com.ga.userapi.model.User;
import com.ga.userapi.model.UserProfile;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserProfileRepository;
import com.ga.userapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    @InjectMocks
    UserProfileServiceImpl profileService;

    @Mock
    UserProfileRepository userProfileRepository;

    @Mock
    UserRepository userRepository;

    private User user;
    private UserProfile userProfile;

    @Before
    public void initData() {
        user = new User();
        user.setUserId(1L);
        user.setPassword("123456");
        user.setUsername("batman");
        user.setEmail("test@test.com");

        userProfile = new UserProfile();
        userProfile.setProfileId(2L);
        userProfile.setAddress("124");
        userProfile.setEmail("aa@aa.com");
        userProfile.setMobile("333-333-3333");
        user.setUserProfile(userProfile);
    }


    @Test
    public void addProfile_UserProfile_SUCCESS(){
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(userProfileRepository.save(any())).thenReturn(userProfile);
        UserProfile dummyProfile = profileService.addUserProfile(user.getUsername(), userProfile);
        assertEquals(dummyProfile.getProfileId(), userProfile.getProfileId());
    }

    @Test
    public void getUserProfile_UserProfile_SUCCESS(){
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(userProfileRepository.findUserProfileByProfileId(any())).thenReturn(userProfile);
        UserProfile dummyProfile = profileService.getUserProfile(user.getUsername());
        assertEquals(dummyProfile.getProfileId(), userProfile.getProfileId());
    }

    @Test
    public void getProfileIdByUsername_Long_FAILURE(){
        when(userRepository.findByUsername(any())).thenReturn(null);
        Long dummyProfileId = profileService.getProfileIdByUsername(user.getUsername());
        assertEquals(new Long(-1L), dummyProfileId);
    }

    @Test
    public void updateUserProfile_UserProfile_SUCCESS(){
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(userProfileRepository.findUserProfileByProfileId(any())).thenReturn(userProfile);
        when(userProfileRepository.save(any())).thenReturn(userProfile);
        UserProfile dummyProfile = profileService.updateUserProfile(user.getUsername(), userProfile);
        assertEquals(dummyProfile.getProfileId(), userProfile.getProfileId());
    }

    @Test
    public void updateUserProfile_UserProfile_FAILURE(){
        when(userRepository.findByUsername(any())).thenReturn(null);
        UserProfile dummyProfile = profileService.updateUserProfile(user.getUsername(), userProfile);
        assertNull(dummyProfile);
    }


//    @Override
//    public UserProfile updateUserProfile(String username, UserProfile updateProfile) {
//        Long profileId = getProfileIdByUsername(username);
//        System.out.println(profileId);
//        if(profileId != -1L){
//            UserProfile up = userProfileRepository.findUserProfileByProfileId(profileId);
//            if(updateProfile.getAddress() != null) up.setAddress(updateProfile.getAddress());
//            if(updateProfile.getEmail() != null) up.setEmail(updateProfile.getEmail());
//            if(updateProfile.getMobile() != null) up.setMobile(updateProfile.getMobile());
//            return userProfileRepository.save(up);
//        }
//        else {
//            return null;
//        }
//    }


}
