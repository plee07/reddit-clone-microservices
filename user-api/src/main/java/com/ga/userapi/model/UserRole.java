package com.ga.userapi.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserRole {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<User> users;

    public UserRole() {}

    public void addUser(User user){
        if(users == null)
            users = new ArrayList<>();
        users.add(user);
    }

    public void setUsers(List<User> users){ this.users = users; }

    public List<User> getUsers(){ return users; }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}