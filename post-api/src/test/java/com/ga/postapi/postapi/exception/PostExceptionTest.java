package com.ga.postapi.postapi.exception;

import com.ga.postapi.postapi.controller.PostController;
import com.ga.postapi.postapi.model.Post;
import com.ga.postapi.postapi.service.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class PostExceptionTest {
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
                .setControllerAdvice(GlobalExceptionHandler.class).build();
    }
    @Test
    public void createComment_Comment_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "123456token")
                .header("username" , "mike")
                .header("userId", 1L)
                .content("{}");

        when(postService.createPost(any(), anyString(),anyString())).thenReturn(post);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
