package com.ga.emailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.emailservice.model.Comment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    RabbitTemplate template;

    @Autowired
    private JavaMailSender javaMailSender;

    private ObjectMapper json = new ObjectMapper();

    @RabbitListener(queuesToDeclare = @Queue("notifyOP"))
    public void notifyOriginalPoster(String message) throws JsonProcessingException {
        Comment comment;
        // save comment details
        System.out.println(message);

        try{
            comment = (Comment) json.readValue(message, Comment.class);
            System.out.println(comment.getPostId() + " " + comment.getText());
        } catch (Exception e){
            System.out.println("ERROR THROWN");
        }

        // get post details

        // get user details

        // send mail
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("lee.phil@outlook.com");
//
//        msg.setSubject("This is a test from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email!");
//
//        javaMailSender.send(msg);
    }
}
