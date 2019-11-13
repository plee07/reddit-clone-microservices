package com.ga.commentapi.commentapi.model;

public class UserBean {
    private String username;

    public UserBean(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
