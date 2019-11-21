package com.ga.postapi.postapi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PostApiApplication {

	@RequestMapping("/")
	public String home() {
		return "post test";
	}
	private final static String QUEUE_NAME = "queue1";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PostApiApplication.class, args);

		// Set up a connection to the queue on localhost
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("198.168.99.100");
		// try-with-resources to connect
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {

			// Declaring a queue is idempotent - it will only be created if it doesn't exist already.
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			// send the message
			String message = "Hello World!";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

			// inform the user via the console
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

}