
package com.ga.user.userapi.repository;

import com.ga.user.userapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findByEmail(String email);

}