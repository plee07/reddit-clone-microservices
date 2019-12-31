package com.ga.postapi.postapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.postapi.postapi.model.Post;
import org.springframework.http.HttpStatus;

public interface PostService {

    public String getPost(String message) throws JsonProcessingException;

    public Post createPost(Post post,String id, String username);

    public HttpStatus deletePost(Long PostId);

    public Iterable<Post> PostList();

    public Iterable<Post> getPostByUserId(Long userId);

    public Iterable<Post> getPostsByUsername(String username);

    public String confirmId(String message) throws Exception;
}
