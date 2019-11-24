package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.Comment;
import com.ga.commentapi.commentapi.model.UserBean;
import com.ga.commentapi.commentapi.repository.CommentRepository;
import org.springframework.amqp.core.AmqpTemplate;
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
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Comment createComment(Long postId, Comment comment, String id, String username) {
        String message = "checkPostId:" + postId;
        System.out.println("Sending message: " + message);
        String postIdcheck = (String) rabbitTemplate.convertSendAndReceive("checkPostId",message);
        System.out.println("COMMEN SIDE " + postIdcheck);
        if(!postIdcheck.equals("NOT_FOUND")){
            comment.setPostId(postId);
            comment.setUserId(Long.parseLong(id));
            comment.setUsername(username);
            comment.setUser(new UserBean(username));
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
