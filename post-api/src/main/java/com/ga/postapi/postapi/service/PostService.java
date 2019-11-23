package com.ga.postapi.postapi.service;

import com.ga.postapi.postapi.model.Post;
import org.springframework.http.HttpStatus;

public interface PostService {

    public Post getPost(Long postId);

    public Post createPost(Post post,String id, String username);

    public HttpStatus deletePost(Long PostId);

    public Iterable<Post> PostList();

    public Iterable<Post> getPostByUserId(String username, Long userId);

    public Iterable<Post> getPostsByUsername(String username);

    public String confirmId(String message) throws Exception;
}
