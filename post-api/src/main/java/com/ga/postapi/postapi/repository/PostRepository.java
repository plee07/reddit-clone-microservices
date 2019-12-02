package com.ga.postapi.postapi.repository;

import com.ga.postapi.postapi.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>  {
    @Transactional
    @Modifying
    public Post findPostByPostId(Long postId);
    public Iterable<Post> findPostsByUserId(Long userId);
    public Iterable<Post> findPostsByUsername(String username);

}
