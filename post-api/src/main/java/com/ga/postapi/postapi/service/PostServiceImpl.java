package com.ga.postapi.postapi.service;


import com.ga.postapi.postapi.config.JwtUtil;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.repository.PostRepository;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public Post createPost(Post post, String jwtToken) {
        System.out.println(jwtUtil.getUsernameFromToken(jwtToken.substring(6)));
        return postRepository.save(post);
    }

//    @Override
//    public User createPost(String username, Post post) {
//        User user = getUser(username);
//        postRepository.save(post);
//        user.addPost(post);
//        return userRepository.save(user);
//    }

//    @Override
//    public User deletePost(String username, Long postId) {
//        User user = getUser(username);
//        Post song = postRepository.findById(postId).get();
//        user.getPost().remove(song);
////        songRepository.deleteById(songId);
//        return userRepository.save(user);
//    }

    @Override
    public HttpStatus deletePost(Long postId) {
        postRepository.deleteById(postId);
        return HttpStatus.OK;
    }


    @Override
    public Iterable<Post> PostList() {
        return postRepository.findAll();
    }
}