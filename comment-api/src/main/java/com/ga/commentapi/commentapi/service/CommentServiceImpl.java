package com.ga.commentapi.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.commentapi.model.Comment;
import com.ga.commentapi.commentapi.model.UserBean;
import com.ga.commentapi.commentapi.repository.CommentRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment createComment(long postId, Comment comment, String id, String username) {

        //confirm postid exists
        RestTemplate rt = new RestTemplate();
        String url = "http://post-api:8082/check/{postid}";
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<HttpStatus> result = rt.exchange(url, HttpMethod.GET, entity, HttpStatus.class, postId);
        System.out.println("==================>" + result.getBody().toString());
        if(result.getBody().equals(HttpStatus.FOUND)){
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
    public Iterable<Comment> deleteCommentByPostId(String message) {
        String postIdJson = "";
//        if (message.startsWith("deleteCommentByPostId")) {
            Long postId = Long.parseLong(message.split(":")[1]);
            return commentRepository.deleteCommentsByPostId(postId);
//        }

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
