package com.ga.userapi;

import com.ga.userapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class UserApiApplication {

	@Autowired
	UserRoleRepository roleRepository;

	@RequestMapping("/")
	public String home() {
		return "usr test";
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
}