
package com.ga.userapi.repository;

import com.ga.userapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findUserByUserId(Long userId);

}