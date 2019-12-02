package com.example.apigateway.service;

import com.example.apigateway.model.User;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.security.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    private User user;

    @Before
    public void init(){
        user = new User();

        user.setUsername("batcat");
        user.setPassword("123456");
    }

    @Test
    public void loadUserByUserName_SUCCESS(){
        when(userRepository.getUserByUsername(any())).thenReturn(user);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("123");
        UserDetails test = userService.loadUserByUsername(user.getUsername());
        assertEquals(user.getUsername(), test.getUsername());

    }

    @Test
    public void userSetter_SUCCESS(){
        User testUser = new User();
        testUser.setUserId(1L);
        assertEquals(new Long(1L), testUser.getUserId());
    }

}
