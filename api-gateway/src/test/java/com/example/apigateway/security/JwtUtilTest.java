package com.example.apigateway.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;


import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JwtUtilTest {

    @InjectMocks
    JwtUtil jwtUtil;

    @Mock
    UserDetails userDetails;

    private String token;

    @Before

    public void init(){
        // a test utility allowing us to change a private value - @Value seems to be not pulling from the test properties file
        ReflectionTestUtils.setField(jwtUtil,"secret", "pancake");
        when(userDetails.getUsername()).thenReturn("batman");
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    public void getUserNameFromToken_SUCCESS(){
        String test = jwtUtil.getUsernameFromToken(token);
        assertEquals("batman", test);
    }

    @Test
    public void validateToken_SUCCESS(){
        when(userDetails.getUsername()).thenReturn("batman");
        boolean test = jwtUtil.validateToken(token, userDetails);
        assertTrue(test);
    }

}
