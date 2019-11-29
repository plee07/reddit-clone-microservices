package com.ga.postapi.postapi.model;

public class UserBean {
    private String username;

    public UserBean(String username){
        this.username = username;
    }

    public UserBean(){};
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
