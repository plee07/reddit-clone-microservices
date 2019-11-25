package com.ga.userapi.repository;

import com.ga.userapi.model.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findUserProfileByProfileId(Long profileId);

}
