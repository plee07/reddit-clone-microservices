package com.ga.postapi.postapi.controller;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String username){
        return username;
    }

    @GetMapping("/list")
    public Iterable<Post> getPost()
    {
        return postService.PostList();
    }

    @PostMapping("/post")
    public Post createPost(@Valid @RequestBody Post post, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return postService.createPost(post, id, username);
    }

    @DeleteMapping("/{postId}")
    public HttpStatus deletePost(@PathVariable Long postId)
    {
        return postService.deletePost(postId);
    }

    @GetMapping("/user/{userId}")
    public Iterable<Post> getPostByUserId(@PathVariable Long userId, @RequestHeader("username") String username)
    {
        return postService.getPostByUserId(username,userId);
    }

    @GetMapping("/user")
    public Iterable<Post> getPostByUsername(@RequestHeader("username") String username)
    {
        return postService.getPostsByUsername(username);
    }

}
