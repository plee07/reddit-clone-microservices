package com.ga.postapi.postapi.repository;

import com.ga.postapi.postapi.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>  {
    public Iterable<Post> findPostsByUserId(Long userId);
}
