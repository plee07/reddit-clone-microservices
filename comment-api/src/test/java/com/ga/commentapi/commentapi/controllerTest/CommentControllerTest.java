package com.ga.commentapi.commentapi.controllerTest;

import com.ga.commentapi.commentapi.controller.CommentController;
import com.ga.commentapi.commentapi.model.commentModel;
import com.ga.commentapi.commentapi.service.CommentServiceImpl;
import org.apache.http.protocol.HTTP;
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
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.junit.jupiter.api.AssertEquals.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {
    private MockMvc mockMvc;
    private commentModel fakeComment;


    @InjectMocks
    CommentController commentController;

    @InjectMocks
    commentModel commentModel;

    @Mock
    CommentServiceImpl commentService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

            fakeComment = new commentModel();
            fakeComment.setCommentId(1L);
            fakeComment.setPostId(1L);
            fakeComment.setText("Lorem Ipsum");
            fakeComment.setUserId(1L);
        }


//    @Before
//    public void initializeCommentController() {
//        this.commentController = new CommentController();
//        commentController.setUserProfileService(new CommentServiceStub());
//    }

    @Test
    public void getCommentsByPostId_Comment_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/post/1/comment")
                .header("Authorization", "username")
                .accept(MediaType.APPLICATION_JSON);

        when(commentService.getCommentsByPostId(anyLong())).thenReturn(new ArrayList<commentModel>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

//    @Test
//    public void createComment_Comment_success() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/post/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "123456token")
//                .content("{\"text\":\"foobar\"}");
//
//        when(commentService.createComment(anyLong(), any(), anyString(),anyString())).thenReturn(new commentModel());
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json("{}"));
//    }


    @Test
    public void deleteComment_Comment_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("Authorization", "faketokten12345")
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

        when(commentService.getCommentsByUsername(anyString())).thenReturn(new ArrayList<commentModel>());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}

