package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.UserBean;
import com.ga.commentapi.commentapi.model.commentModel;
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
    public commentModel createComment(Long postId, commentModel comment, String id, String username) {

        //confirm postid exists
//        RestTemplate rt = new RestTemplate();
//        String url = "http://post-api:8082/check/{postid}";
//        HttpHeaders headers = new HttpHeaders();

//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//        ResponseEntity<HttpStatus> result = rt.exchange(url, HttpMethod.GET, entity, HttpStatus.class, postId);
//        System.out.println("==================>" + result.getBody().toString());
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
    public Iterable<commentModel> getCommentsByPostId(long postId) {
        Iterable<commentModel> commentList = commentRepository.findCommentByPostId(postId);
        for(commentModel comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

    @Override
    public Iterable<commentModel> getCommentsByUserId(String username, Long userId) {
        Iterable<commentModel> commentList = commentRepository.findCommentByUserId(userId);
        for(commentModel comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

    @Override
    public Iterable<commentModel> getCommentsByUsername(String username) {
        Iterable<commentModel> commentList = commentRepository.findCommentsByUsername(username);
        for(commentModel comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

}

