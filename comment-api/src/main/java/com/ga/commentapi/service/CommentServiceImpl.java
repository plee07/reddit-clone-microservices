package com.ga.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.commentapi.exception.PostNotFoundException;
import com.ga.commentapi.model.CommentModel;
import com.ga.commentapi.model.UserBean;
import com.ga.commentapi.repository.CommentRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper json = new ObjectMapper();

    @Override
    public CommentModel createComment(Long postId, CommentModel comment, String id, String username)
            throws JsonProcessingException, PostNotFoundException {
        String message = "checkPostId:" + postId;
        String postIdCheck = (String) rabbitTemplate.convertSendAndReceive("checkPostId",message);
        System.out.println("COMMEN SIDE " + postIdCheck);
        if(!postIdCheck.equals("NOT_FOUND")){
            comment.setPostId(postId);
            comment.setUserId(Long.parseLong(id));
            comment.setUsername(username);
            UserBean ub = new UserBean();
            ub.setUsername(username);
            ub.getUsername();
            comment.setUser(ub);
            if(comment.isNotifyOP()){
                String jsonComment = json.writeValueAsString(comment);
                rabbitTemplate.convertAndSend("notifyOP", jsonComment);
            }
            return commentRepository.save(comment);
        } else throw new PostNotFoundException("Post does not exits");
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
        commentRepository.deleteCommentsByPostId(postId);
    }

    @Override
    public Iterable<CommentModel> getCommentsByPostId(long postId) {
        Iterable<CommentModel> commentList = commentRepository.findCommentByPostId(postId);
        for(CommentModel comment : commentList){
            UserBean ub = new UserBean();
            ub.setUsername(comment.getUsername());
            comment.setUser(ub);
        }
        return commentList;
    }

    @Override
    public Iterable<CommentModel> getCommentsByUsername(String username) {
        Iterable<CommentModel> commentList = commentRepository.findCommentsByUsername(username);
        for(CommentModel comment : commentList){
            UserBean ub = new UserBean();
            ub.setUsername(comment.getUsername());
            comment.setUser(ub);
        }
        return commentList;
    }
}

