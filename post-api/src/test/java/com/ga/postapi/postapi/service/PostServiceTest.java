package com.ga.postapi.postapi.service;

import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.repository.PostRepository;
import com.ga.postapi.postapi.service.PostServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PostServiceTest {

    private Post mockPost = new Post();
    private List<Post> postList = new ArrayList<>();

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @InjectMocks
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

//    @Test
//    public void confirmId_IdFound_Success()
//    {
//        when(postRepository.findById(mockPost.getPostId())).thenReturn();
//    }
}
