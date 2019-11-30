package com.ga.postapi.postapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@EnableSwagger2
public class PostApiApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PostApiApplication.class, args);

	}

}