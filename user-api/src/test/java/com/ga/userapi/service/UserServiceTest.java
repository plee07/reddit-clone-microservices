package com.ga.userapi.service;


import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.exception.IncorrectLoginException;
import com.ga.userapi.model.User;
import com.ga.userapi.model.UserProfile;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserRepository;
import com.ga.userapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserRoleRepository roleRepository;

    @Mock
    UserProfile userProfile;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRole ur;

    @Before
    public void initData() {
        MockitoAnnotations.initMocks(this);
        user.setUserId(1L);
        user.setPassword("123");
        user.setUsername("batman");

        userProfile.setAddress("124");
        userProfile.setEmail("aa@aa.com");
        userProfile.setMobile("333-333-3333");
        user.setUserProfile(userProfile);

        ur = new UserRole();
        ur.setName("ROLE_USER");
        ur.setRoleId(1);
        ur.addUser(user);

        user.addRole(ur);
    }

    @Test
    public void signup_String_SUCCESS() {
        String expectedToken = "12345";

        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("robin");
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);

        String actualToken = userService.signup(user);

        assertEquals(actualToken, expectedToken);
    }

    @Test
    public void login_User_Success() {
        String expectedToken = "12345";

        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("robin");
        when(roleRepository.findByName(any())).thenReturn(ur);
        when(userRepository.save(any())).thenReturn(user);


        String actualToken = userService.login(user);
        assertEquals(actualToken, expectedToken);
    }

//    @Test(expected = IncorrectLoginException.class)
//    public void login_User_ThrowIncorrectLoginException() {
//
//        when(userDao.login(any())).thenReturn(null);
//        try {
//            userService.login(user);
//        } finally {
//        }

}