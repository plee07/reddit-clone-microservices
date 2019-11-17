package com.ga.user.userapi.repository;

import com.ga.user.userapi.model.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findUserProfileByProfileId(Long profileId);

}
