package com.ga.postapi.postapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableScheduling
public class PostApiApplication {

	@RequestMapping("/")
	public String home() {
		return "post test";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PostApiApplication.class, args);

	}

}