package com.ga.postapi.postapi.controller;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post")
    public Iterable<Post> getPost()
    {
        return postService.PostList();
    }

    @PostMapping("/{username}/post")
    public User addPost(@RequestBody String username, Post post)
    {
        return postService.addPost(username, post);
    }


}
