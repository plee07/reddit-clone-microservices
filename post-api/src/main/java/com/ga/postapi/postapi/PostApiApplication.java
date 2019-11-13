package com.ga.postapi.postapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PostApiApplication {

	@RequestMapping("/")
	public String home() {
		return "post test";
	}

	public static void main(String[] args) {
		SpringApplication.run(PostApiApplication.class, args);
	}

}