package com.ga.postapi.postapi.service;


import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.model.UserBean;
import com.ga.postapi.postapi.repository.PostRepository;
import org.springframework.amqp.core.AmqpTemplate;
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
//    @Override
//    public User getUser(String username) {
//        return userRepository.getUserByUsername(username);
//    }
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

        amqpTemplate.convertAndSend("deleteCommentsByPostId",postId);
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
    public HttpStatus confirmId(Long postId) {
        Post post;
        try{
            post = postRepository.findById(postId).get();
        } catch(Exception e) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.FOUND;
    }
}