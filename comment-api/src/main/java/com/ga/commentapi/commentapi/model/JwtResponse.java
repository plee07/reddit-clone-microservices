package com.ga.commentapi.commentapi.model;

public class JwtResponse {

    private String jwt;
    private String username;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.jwt = token;
    }

    public String getToken() {
        return this.jwt;
    }
}