
package com.example.apigateway.repository;

import com.example.apigateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
    }

}