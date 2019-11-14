package com.ga.postapi.postapi.service;


import com.ga.postapi.postapi.config.JwtUtil;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.model.UserBean;
import com.ga.postapi.postapi.repository.PostRepository;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtUtil jwtUtil;

//    @Override
//    public User getUser(String username) {
//        return userRepository.getUserByUsername(username);
//    }

    @Override
    public User getUser(String username) {
        return null;
    }

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
        postRepository.deleteById(postId);
        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/comments/deleteBy/{postid}";
        HttpHeaders headers = new HttpHeaders();

//        rt.getForObject("http://comments:8083/deleteBy/" + postId, String.class);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = rt.exchange(url, HttpMethod.GET, entity, String.class, postId);
        return HttpStatus.OK;
    }


    @Override
    public Iterable<Post> PostList() {
        Iterable<Post> postList = postRepository.findAll();
        for(Post post : postList){
            post.setUser(new UserBean(post.getUsername()));
        }
        return postList;
//        return postRepository.findAll();
    }
}