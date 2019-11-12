package com.ga.postapi.postapi.service;

import com.ga.postapi.postapi.model.Post;
import org.apache.tomcat.jni.User;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PostService {

    public User getUser(String username);

    public Post getPost(Long postId);

    public User createPost(String username, Post post);

    public HttpStatus deletePost(Long PostId);

    public Iterable<Post> PostList();
}
