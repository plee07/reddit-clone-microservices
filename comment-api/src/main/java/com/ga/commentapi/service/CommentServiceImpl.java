package com.ga.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentapi.model.Comment;
import com.ga.commentapi.model.UserBean;
import com.ga.commentapi.repository.CommentRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper json = new ObjectMapper();

    @Override
    public Comment createComment(Long postId, Comment comment, String id, String username) throws JsonProcessingException {
        String message = "checkPostId:" + postId;
        System.out.println("Sending message: " + message);
        String postIdCheck = (String) rabbitTemplate.convertSendAndReceive("checkPostId",message);
        System.out.println("COMMEN SIDE " + postIdCheck);
        if(!postIdCheck.equals("NOT_FOUND")){
            comment.setPostId(postId);
            comment.setUserId(Long.parseLong(id));
            comment.setUsername(username);
            comment.setUser(new UserBean(username));

            //if notify OP is true, send email
            if(comment.isNotifyOP()){
                String jsonComment = json.writeValueAsString(comment);
                rabbitTemplate.convertAndSend("notifyOP", jsonComment);
            }

            return commentRepository.save(comment);
        } else return null;
    }

    @Override
    public HttpStatus deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
        return HttpStatus.OK;
    }

    @Override
    @RabbitListener(queuesToDeclare = @Queue("deleteCommentByPostId"))
    public void deleteCommentByPostId(String message) {
        Long postId = Long.parseLong(message.split(":")[1]);
        try {
            commentRepository.deleteCommentsByPostId(postId);
        } catch(Exception e){

        }
    }

    @Override
    public Iterable<Comment> getCommentsByPostId(long postId) {
        Iterable<Comment> commentList = commentRepository.findCommentByPostId(postId);
        for(Comment comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

    @Override
    public Iterable<Comment> getCommentsByUserId(String username, Long userId) {
        Iterable<Comment> commentList = commentRepository.findCommentByUserId(userId);
        for(Comment comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

    @Override
    public Iterable<Comment> getCommentsByUsername(String username) {
        Iterable<Comment> commentList = commentRepository.findCommentsByUsername(username);
        for(Comment comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

}
