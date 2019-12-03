package com.ga.emailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.emailservice.model.CommentBean;
import com.ga.emailservice.model.PostBean;
import com.ga.emailservice.model.UserBean;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    RabbitTemplate template;

    @Autowired
    private JavaMailSender javaMailSender;

    private ObjectMapper json = new ObjectMapper();

    @RabbitListener(queuesToDeclare = @Queue("notifyOP"))
    public void notifyOriginalPoster(String message) throws JsonProcessingException {
        CommentBean comment;
        PostBean post;
        UserBean user;
        System.out.println(message);

        try{
            // save comment details
            comment = (CommentBean) json.readValue(message, CommentBean.class);
            System.out.println("DOES IT REACH HERE +==>" + comment.getText());
            // retrieve post details
            String postId = String.valueOf(comment.getPostId());
            String postJson = (String) template.convertSendAndReceive("getPostById", postId);
            System.out.println(postJson);
            post = json.readValue(postJson, PostBean.class);
            System.out.println("DOES IT REACH HERE POST+==>" + post.getDescription());

            // retrieve original poster details
            String userId = String.valueOf(post.getUserId());
            System.out.println("USER ID IS: " + userId);
            String userJson = (String) template.convertSendAndReceive("getUserById", userId);
            System.out.println("USER:" + userJson);
            user = json.readValue(userJson, UserBean.class);

            System.out.println(user.getEmail());

            // send mail
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(user.getEmail());
//            System.out.println(post.getTitle() + " " + comment.)
            msg.setSubject("Someone responded to your post - " + post.getTitle());
            msg.setText("Hi " + user.getUsername() + "\n\n" +
                    comment.getUser().getUsername() + " responded to your post - " + post.getTitle() + "\n\n" + "Comment: " + comment.getText()
            );

            javaMailSender.send(msg);

        } catch (Exception e){
            System.out.println("ERROR THROWN");
        }

    }
}
