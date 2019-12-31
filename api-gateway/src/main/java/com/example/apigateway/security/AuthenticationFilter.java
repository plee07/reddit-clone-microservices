package com.example.apigateway.security;
import com.example.apigateway.model.User;
import com.example.apigateway.service.UserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationFilter extends ZuulFilter {
    @Autowired
    private UserService userService;

    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (username != "anonymousUser");
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        if(user != null)  ctx.addZuulRequestHeader("userId", String.valueOf(user.getUserId()));
        ctx.addZuulRequestHeader("username", username);
        return ctx;
    }
}