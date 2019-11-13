package com.ga.postapi.postapi.service;


import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.model.UserBean;
import com.ga.postapi.postapi.repository.PostRepository;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
//        System.out.println(jwtUtil.getUsernameFromToken(jwtToken.substring(6)));
        post.setUserId(Long.parseLong(id));
        post.setUsername(username);
        return postRepository.save(post);
    }

    @Override
    public HttpStatus deletePost(Long postId) {
        postRepository.deleteById(postId);
        return HttpStatus.OK;
    }


    @Override
    public Iterable<Post> PostList() {
        Iterable<Post> postList = postRepository.findAll();
        for(Post post : postList){
            UserBean user = new UserBean(post.getUsername());
            post.setUser(user);
        }
        return postList;
//        return postRepository.findAll();
    }
}