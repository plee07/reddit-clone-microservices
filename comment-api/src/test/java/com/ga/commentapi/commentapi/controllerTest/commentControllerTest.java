package com.ga.commentapi.commentapi.controllerTest;

import com.ga.commentapi.commentapi.controller.CommentController;
import com.ga.commentapi.commentapi.model.commentModel;
import com.ga.commentapi.commentapi.service.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

//import static org.junit.jupiter.api.AssertEquals.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class commentControllerTest {
    private MockMvc mockMvc;


    @InjectMocks
    CommentController commentController;

    @InjectMocks
    commentModel commentModel;

    @Mock
    CommentServiceImpl commentService;

//    @Before
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
//
//    }

//    @Before
//    public void initializeCommentController() {
//        this.commentController = new CommentController();
//        commentController.setUserProfileService(new CommentServiceStub());
//    }

    @Test
    public void createComment_HelloWorld_Success() {
        commentModel comment = new commentModel();
        comment.setText("Hello world");
        comment.setPostId(1L);
        comment.setUserId(1L);
        commentModel tempcomment = commentController.createComment(1L,comment,"max","1");
        Assert.assertNull(tempcomment);
    }

//    @Test
//    public void getCommentsByPostId_Comment_Success() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/post/1/comment")
//                .header("Authorization", "faketokten12345")
//                .accept(MediaType.APPLICATION_JSON);
//
//        when(commentService.getCommentsByPostId(anyLong())).thenReturn(new ArrayList<Comment>());
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"));
//    }
//
//    @Test
//    public void createComment_Comment_success() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/comment/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "123456token")
//                .content("{\"text\":\"foobar\"}");
//
//        when(commentService.createComment(anyLong(), any(), anyString())).thenReturn(new Comment());
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json("{}"));
//    }
//
//
//    @Test
//    public void deleteComment_Comment_Success() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete("/comment/1")
//                .header("Authorization", "faketokten12345")
//                .accept(MediaType.APPLICATION_JSON);
//
//        when(commentService.deleteComment(anyLong(), anyString())).thenReturn(new Comment());
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json("{}"));
//    }
}

