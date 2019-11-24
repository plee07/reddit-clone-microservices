package com.ga.postapi.postapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.model.UserBean;
import com.ga.postapi.postapi.repository.PostRepository;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

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
        rabbitTemplate.convertAndSend("deleteCommentByPostId",message);
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
    public String confirmId(String message) throws JsonProcessingException {
        Long postId = Long.parseLong(message.split(":")[1]);

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