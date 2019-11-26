package com.ga.userapi.controller;
import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.model.User;
import com.ga.userapi.services.UserService;
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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private User user;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Before
    public void initializeUser() {
        user = new User();

        user.setUsername("test");;
    }

    @Test
    public void signup_User_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("test","tester", "tester@tester.com"));

        when(userService.signup(any())).thenReturn("1234");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"1234\"}"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void login_User_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("test", "tester", "tester@test.com"));

        when(userService.login(any())).thenReturn("1234");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"1234\"}"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    private static String createUserInJson(String username, String password, String email) {
        return "{ \"username\": \"" + username + "\", " +
                "\"password\":\"" + password + "\", " +
                "\"email\":\"" + email + "\"" +
                "}";
    }



}