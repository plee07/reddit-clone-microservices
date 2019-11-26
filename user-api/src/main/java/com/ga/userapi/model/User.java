package com.ga.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username must be provided")
    private String username;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email must be provided")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password must be provided")
    @Size(min = 6, message = "Password must be greater than size 6")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    @JsonIgnore
    private UserProfile userProfile;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "user_role_table",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
            )
    private List<UserRole> roles;

    public void addRole(UserRole role){
        if(roles == null)
            roles = new ArrayList<>();
        roles.add(role);
    }


    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}