package com.ga.commentapi.exceptiontest;

import com.ga.commentapi.controller.CommentController;
import com.ga.commentapi.exception.GlobalExceptionHandler;
import com.ga.commentapi.model.CommentModel;
import com.ga.commentapi.service.CommentServiceImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CommentExceptionTest {
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
                  .setControllerAdvice(GlobalExceptionHandler.class).build();
    }
    @Test
    public void createComment_Comment_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "123456token")
                .header("username" , "mike")
                .header("userId", 1L)
                .content("{}");

       when(commentService.createComment(anyLong(), any(), anyString(),anyString())).thenReturn(commentModel);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
