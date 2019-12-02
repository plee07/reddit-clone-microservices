package com.example.apigateway.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.apigateway.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(MockitoJUnitRunner.class)
public class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter reqFilter;
    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserDetails userDetails;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse res;
    @Mock
    private FilterChain chain;
    @Mock
    private Authentication auth;
    @Mock
    private SecurityContext securityContext;

    final OutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void doFilterInternal_SUCCESS() throws ServletException, IOException {
        when(req.getHeader(any())).thenReturn("Bearer 1234");
        when(jwtUtil.getUsernameFromToken(any())).thenReturn("testUser");
//        when(securityContext.getAuthentication()).thenReturn(auth);
        when(userService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtUtil.validateToken(any(),any())).thenReturn(true);

        reqFilter.doFilterInternal(req, res, chain);
    }

    @Test
    public void doFilterInternal_IllegalArgument() throws ServletException, IOException, IllegalArgumentException {
        when(req.getHeader(any())).thenReturn("Bearer 1234");
        when(jwtUtil.getUsernameFromToken(any())).thenThrow(IllegalArgumentException.class);
        reqFilter.doFilterInternal(req, res, chain);
//        assertEquals("JWT Token has expired", outContent.toString());
    }

    @Test
    public void doFilterInternal_ExpiredToken() throws ServletException, IOException {
        when(req.getHeader(any())).thenReturn("Bearer 1234");
        when(jwtUtil.getUsernameFromToken(any())).thenThrow(ExpiredJwtException.class);
        reqFilter.doFilterInternal(req, res, chain);
//        assertEquals("JWT Token has expired", outContent.toString());
    }


}
