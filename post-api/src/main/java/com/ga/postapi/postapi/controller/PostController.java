package com.ga.postapi.postapi.controller;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    @RestResource(path = "/list")
    @ApiOperation("This returns a list of post")
    public Iterable<Post> getPost()
    {
        return postService.PostList();
    }

    @PostMapping("/post")
    @RestResource(path = "/post")
    @ApiOperation("This creates your post")
    public Post createPost(@Param("post, username, userId") @Valid @RequestBody Post post, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return postService.createPost(post, id, username);
    }

    @DeleteMapping("/{postId}")
    @RestResource(path = "/{postId}")
    @ApiOperation("Lets you delete a post. Interservice call where comments get deleted with post.")
    public HttpStatus deletePost(@Param("postId") @PathVariable Long postId)
    {
        return postService.deletePost(postId);
    }

    @GetMapping("/user/{userId}")
    @RestResource(path = "/user/{userId}")
    public Iterable<Post> getPostByUserId(@PathVariable Long userId)
    {
        return postService.getPostByUserId(userId);
    }

    @GetMapping("/user")
    @RestResource(path = "/user")
    @ApiOperation("Gets a list of post written by that user")
    public Iterable<Post> getPostByUsername(@RequestHeader("username") String username)
    {
        return postService.getPostsByUsername(username);
    }

}
