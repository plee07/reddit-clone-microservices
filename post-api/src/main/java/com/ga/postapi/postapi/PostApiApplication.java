package com.ga.postapi.postapi;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PostApiApplication {

	@Autowired
	PostService postService;

	@RequestMapping("/")
	public String home() {
		return "post test";
	}

	@GetMapping("/post")
	public Iterable<Post> getPost()
	{
		return postService.PostList();
	}

	@PostMapping("/{username}/post")
	public User addPost(@RequestBody String username, Post post)
	{
		return postService.createPost(username, post);
	}


	public static void main(String[] args) {
		SpringApplication.run(PostApiApplication.class, args);
	}

}
