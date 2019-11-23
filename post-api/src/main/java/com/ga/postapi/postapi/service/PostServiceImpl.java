package com.ga.postapi.postapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.model.UserBean;
import com.ga.postapi.postapi.repository.PostRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId).get();
    }

    @Override
    public Post createPost(Post post, String id, String username) {

        post.setUserId(Long.parseLong(id));
        post.setUsername(username);
        post.setUser(new UserBean(username));
        return postRepository.save(post);
    }

    @Override
    public HttpStatus deletePost(Long postId) {
        String message = "deleteCommentByPostId:" + postId;
        System.out.println("Sending message: " + message);
        rabbitTemplate.convertAndSend("deleteCommentByPostId",message);
//        RestTemplate rt = new RestTemplate();
//        String url = "http://comment-api:8083/deleteBy/{postid}";
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//        ResponseEntity<String> result = rt.exchange(url, HttpMethod.GET, entity, String.class, postId);
//        System.out.println("THIS IS DEL" + result);
        postRepository.deleteById(postId);
        return HttpStatus.OK;
    }


    @Override
    public Iterable<Post> PostList() {
        Iterable<Post> postList = postRepository.findAll();
        for(Post post : postList){
            post.setUser(new UserBean(post.getUsername()));
        }
        return postList;
    }

    @Override
    public Iterable<Post> getPostByUserId(String username, Long userId) {

        return postRepository.findPostsByUserId(userId);
    }

    @Override
    public Iterable<Post> getPostsByUsername(String username) {
        Iterable<Post> postList = postRepository.findPostsByUsername(username);
        for(Post post : postList){
            post.setUser(new UserBean(post.getUsername()));
        }
        return postList;
    }

    @Override
    @RabbitListener(queuesToDeclare = @Queue("checkPostId"))
    public String confirmId(String message) throws Exception {
        System.out.println("------------------------->" + message);
        Long postId = Long.parseLong(message.split(":")[1]);
        System.out.println("POSTID =================> " + postId);
        ObjectMapper json = new ObjectMapper();
        String returnVal;
        Post post;
        try{
            post = postRepository.findById(postId).get();
            if(post == null) System.out.println("THIS IS NULL!");
        } catch (Exception e){
            return "NOT_FOUND";
        }
       return json.writeValueAsString(String.valueOf(post.getPostId()));
    }
}