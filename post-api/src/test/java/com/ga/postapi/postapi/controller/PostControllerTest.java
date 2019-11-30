package com.ga.postapi.postapi.controller;

import com.ga.postapi.postapi.exception.GlobalExceptionHandler;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    PostController postController;

    @InjectMocks
    Post post;

    @Mock
    PostServiceImpl postService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();

    }

    @Test
    public void getPost_ListofPost_Success() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list")
                .header("Authorization", "username")
                .accept(MediaType.APPLICATION_JSON);
        when(postService.PostList()).thenReturn(new ArrayList<>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void deletePost_PostDeleted_Success() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("Authorization", "username");
        when(postService.deletePost(anyLong())).thenReturn(HttpStatus.OK);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getPostByUsername_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .header("username", "money")
                .accept(MediaType.APPLICATION_JSON);

        when(postService.getPostsByUsername(anyString())).thenReturn(new ArrayList<Post>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getPostByUserId_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/{userId}", 1)
                .header("username", "money")
                .accept(MediaType.APPLICATION_JSON);

        when(postService.getPostByUserId(any())).thenReturn(new ArrayList<Post>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    public void createPost_Post_Success() throws Exception {
        Post fakeSavedPost = new Post();
        fakeSavedPost.setTitle("this is a title");
        fakeSavedPost.setDescription("this is a description");
        fakeSavedPost.setUserId(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "123456token")
                .header("username","mom")
                .header("userId",1L)
                .content("{\"title\":\"foo\",\"description\":\"bar\",\"userId\":\"1\"}");

        when(postService.createPost(any(), anyString(),anyString())).thenReturn(fakeSavedPost);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void createEmptyPost_Post_ERROR() throws Exception {
//        Post fakeSavedPost = new Post();
//        fakeSavedPost.setDescription("this is a description");
//        fakeSavedPost.setUserId(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":null,\"description\":\"bar\",\"userId\":\"1\"}");

//        when(postService.createPost(any(), anyString(),anyString())).thenReturn(fakeSavedPost);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}


