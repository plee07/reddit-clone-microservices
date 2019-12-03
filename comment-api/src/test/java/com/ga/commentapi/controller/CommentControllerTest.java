package com.ga.commentapi.controller;

import com.ga.commentapi.exception.GlobalExceptionHandler;
import com.ga.commentapi.exception.PostNotFoundException;
import com.ga.commentapi.model.CommentModel;
import com.ga.commentapi.service.CommentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static org.junit.jupiter.api.AssertEquals.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    CommentController commentController;

    @InjectMocks
    CommentModel commentModel;

    @Mock
    CommentServiceImpl commentService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();

            commentModel = new CommentModel();
            commentModel.setCommentId(1L);
            commentModel.setPostId(1L);
            commentModel.setText("Lorem Ipsum");
            commentModel.setUserId(1L);
    }

    @Test
    public void getCommentsByPostId_Comment_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/post/1/comment")
                .header("Authorization", "username")
                .accept(MediaType.APPLICATION_JSON);

        when(commentService.getCommentsByPostId(anyLong())).thenReturn(new ArrayList<CommentModel>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void createComment_Comment_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/{userid}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("username","mom")
                .header("userId",1L)
                .content("{\n" +
                        "\t\"text\": \"I DONT KNOW\",\n" +
                        "\t\"notifyOP\": false\n" +
                        "}");

        when(commentService.createComment(anyLong(), any(), anyString(),anyString())).thenReturn(new CommentModel());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void createComment_Comment_FAILURE() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/{userid}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("username","mom")
                .header("userId",1L)
                .content("{\"text\": null}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void createComment_Comment_POSTNOTFOUND() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/{userid}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("username","mom")
                .header("userId",1L)
                .content("{\n" +
                        "\t\"text\": \"I DONT KNOW\",\n" +
                        "\t\"notifyOP\": false\n" +
                        "}");

        when(commentService.createComment(anyLong(), any(), anyString(),anyString())).thenThrow(PostNotFoundException.class);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }


    @Test
    public void deleteComment_Comment_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("username","mom")
                .header("userId",1L)
                .accept(MediaType.APPLICATION_JSON);

        when(commentService.deleteComment(anyLong())).thenReturn(HttpStatus.OK);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void getCommentsByUsername_Comment_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .header("username", "money")
                .accept(MediaType.APPLICATION_JSON);

        when(commentService.getCommentsByUsername(anyString())).thenReturn(new ArrayList<CommentModel>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}

