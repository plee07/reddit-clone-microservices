package com.ga.userapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.userapi.config.JwtUtil;
import com.ga.userapi.exception.GlobalExceptionHandler;
import com.ga.userapi.exception.IncorrectLoginException;
import com.ga.userapi.exception.UserAlreadyExistsException;
import com.ga.userapi.model.User;
import com.ga.userapi.model.UserProfile;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserRepository;
import com.ga.userapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {
        GlobalExceptionHandler.class})
public class UserServiceTest {

    private User user;
    private UserProfile userProfile;
    private UserRole ur;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private ObjectMapper json;

    @Mock
    UserRoleRepository roleRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @Before
    public void initData() {
        user = new User();
        user.setUserId(1L);
        user.setPassword("123456");
        user.setUsername("batman");
        user.setEmail("test@test.com");

        userProfile = new UserProfile();
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
        when(userRepository.save(any())).thenReturn(user);

        String actualToken = userService.signup(user);

        assertEquals(actualToken, expectedToken);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void duplicateUsername_signup_ERROR(){
        when(userRepository.findByUsername(any())).thenReturn(user);

        String signup = userService.signup(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void duplicateEmail_signup_ERROR(){
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userRepository.findByEmail(any())).thenReturn(user);

        String signup = userService.signup(user);
    }

    @Test
    public void Username_login_String_SUCCESS() {
        String expectedToken = "12345";
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(true);
        String actualToken = userService.login(user);
        assertEquals(actualToken, expectedToken);
    }

    @Test
    public void Email_login_String_SUCCESS() {
        String expectedToken = "12345";
        user.setUsername(null);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(true);
        String actualToken = userService.login(user);
        assertEquals(actualToken, expectedToken);
    }

    @Test(expected = IncorrectLoginException.class)
    public void login_BadCredentials_ERROR(){
        user.setUsername(null);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(any(),any())).thenReturn(false);
        String actualToken = userService.login(user);
    }

    @Test
    public void getUser_String_SUCCESS() throws JsonProcessingException {
        ObjectMapper json = new ObjectMapper();
        String actual = json.writeValueAsString(user);

        when(userRepository.findUserByUserId(any())).thenReturn(user);

        String userObj = userService.getUser("1");
        assertEquals(actual, userObj);
    }

}
