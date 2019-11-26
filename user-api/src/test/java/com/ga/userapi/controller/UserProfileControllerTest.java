package com.ga.userapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ga.userapi.model.UserProfile;
import com.ga.userapi.services.UserProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {

    private MockMvc mockMvc;
    private UserProfile userProfile;

    @InjectMocks
    UserProfileController userProfileController;

    @Mock
    UserProfileService userProfileService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Before
    public void initializeUserProfile() {
        userProfile = new UserProfile();
        userProfile.setProfileId(1L);
        userProfile.setEmail("batman@superhero.com");
        userProfile.setAddress("Gotham");
        userProfile.setMobile("111-111-1111");
    }

    @Test
    public void getUserProfile_UserProfile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/profile")
                .accept(MediaType.APPLICATION_JSON)
                .header("username", "tester");

        when(userProfileService.getUserProfile(any())).thenReturn(userProfile);


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void addUserProfile_UserProfile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "tester")
                .content(createUserProfileInJson("test", "test", "test"));

        when(userProfileService.addUserProfile((any()), any())).thenReturn(userProfile);
        System.out.println(userProfile.getEmail());

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"email\":\"batman@superhero.com\"}"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateUserProfile_UserProfile_SUCCESS() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "tester")
                .content("{\"email\":\"batman@superhero.com\"}");

        when(userProfileService.updateUserProfile((any()), any())).thenReturn(userProfile);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"email\":\"batman@superhero.com\"}"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    //This converts to JSON object
    private static String createUserProfileInJson(String email, String mobile, String address) {
        return "{ \"email\": \"" + email + "\", " + "\"mobile\":\"" + mobile + "\", " + "\"address\":\"" + address + "\"}";
    }
}