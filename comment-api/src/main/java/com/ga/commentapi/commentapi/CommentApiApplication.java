package com.ga.commentapi.commentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaClient
@RestController
public class CommentApiApplication {

	@RequestMapping("/")
	public String home(@RequestHeader("username") String username, @RequestHeader("userId") String id) {
		return username + " " + id;
	}

	public static void main(String[] args) {
		SpringApplication.run(CommentApiApplication.class, args);
	}

}
