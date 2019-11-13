package com.ga.postapi.postapi.controller;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public Iterable<Post> getPost()
    {
        return postService.PostList();
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody Post post, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return postService.createPost(post, id);
    }

    @PostMapping("/{postId}")
    public HttpStatus deletePost(@RequestBody Long postId)
    {
        return postService.deletePost(postId);
    }


}
