package com.ga.postapi.postapi.repository;

import com.ga.postapi.postapi.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>  {
    public Post findPostByPostId(Long postId);
    public Iterable<Post> findPostsByUserId(Long userId);
    public Iterable<Post> findPostsByUsername(String username);

}
