package com.ga.postapi.postapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.repository.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@RunWith(SpringRunner.class)
public class PostServiceTest {

    private Post mockPost = new Post();
    private List<Post> postList = new ArrayList<>();
    private ObjectMapper json = new ObjectMapper();
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Before
    public void init()
    {
        mockPost.setUserId(1L);
        mockPost.setDescription("Money");
        mockPost.setTitle("Bags");
        mockPost.setPostId(1L);
        mockPost.setUsername("batman");
        postList.add(mockPost);
    }

    @Test
    public void getPostList_PostList_Success()
    {
        when(postRepository.findAll()).thenReturn(postList);
        Iterable<Post> foundPost = postService.PostList();
        Assert.assertEquals(foundPost,postList);
    }

    @Test
    public void createPost_createdPost_Success()
    {
        when(postRepository.save(mockPost)).thenReturn(mockPost);
        Post savedPost = postService.createPost(mockPost,"1","Johnny");
        Assert.assertEquals(savedPost,mockPost);
    }

    @Test
    public void getPostByUsername_GotPost_Success()
    {
        when(postRepository.findPostsByUsername(mockPost.getUsername())).thenReturn(postList);
        Iterable<Post> foundPost = postService.getPostsByUsername(mockPost.getUsername());
        Assert.assertEquals(foundPost,postList);
    }

    @Test
    public void deletePost_HTTPSTATUS_SUCCESS(){
        when(rabbitTemplate.convertSendAndReceive(anyString(),any(Class.class))).thenReturn("OK");
        HttpStatus test = postService.deletePost(1L);
        Assert.assertEquals(HttpStatus.OK.value(), test.value());
    }

    @Test
    public void getPostByUserId_PostList_SUCCESS(){
        when(postRepository.findPostsByUserId(any())).thenReturn(new ArrayList<Post>());
        Iterable<Post> test = postService.getPostByUserId(1L);
        Assert.assertNotNull(test);
    }

    @Test
    public void confirmId_String_SUCCESS() throws JsonProcessingException {
        when(postRepository.findPostByPostId(any())).thenReturn(mockPost);
        String postId = postService.confirmId("Message:1");
        Assert.assertNotEquals("NOT_FOUND", postId);
    }

    @Test
    public void getPost_String_SUCCESS() throws JsonProcessingException {
        when(postRepository.findPostByPostId(any())).thenReturn(mockPost);

        String expected = json.writeValueAsString(mockPost);
        String actual = postService.getPost("1");
        Assert.assertEquals(expected, actual);
    }

}
