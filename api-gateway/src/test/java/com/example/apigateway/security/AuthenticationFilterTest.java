package com.example.apigateway.security;

import com.example.apigateway.model.User;
import com.example.apigateway.service.UserService;
import com.netflix.zuul.context.RequestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    @InjectMocks
    private AuthenticationFilter filter;

    @Mock
    private UserService userService;
    @Mock
    private SecurityContextHolder securityContextholder;
    @Mock
    private SecurityContext ctx;
    @Mock
    private Authentication auth;

    @Before
    public void init(){
        SecurityContextHolder.setContext(ctx);
    }

    @Test
    public void filterType_SUCCESS(){
        String test = filter.filterType();
        assertEquals("pre", test);
    }

    @Test
    public void filterOrder_SUCCESS(){
        int test = filter.filterOrder();
        assertEquals(1, test);
    }

    @Test
    public void shouldFilter_SUCCESS(){
        Mockito.when(ctx.getAuthentication()).thenReturn(auth);
        when(ctx.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("tester");
        boolean test = filter.shouldFilter();
        assertEquals(true, test);
    }

    @Test
    public void run_SUCCESS(){
        when(ctx.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("tester");
        when(userService.getUser(any())).thenReturn(new User(1L, "tester", "pass123"));

        Object ctx = filter.run();
        RequestContext rctx = (RequestContext) ctx;
        assertEquals("tester", rctx.getZuulRequestHeaders().get("username"));
    }

}
