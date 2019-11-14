
package com.ga.user.userapi.repository;

import com.ga.user.userapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM User u WHERE u.username = ?1 and u.password = ?2")
    public User login(String username, String password);

    public User findByUsername(String username);

}