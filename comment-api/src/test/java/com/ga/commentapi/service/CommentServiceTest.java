package com.ga.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.exception.PostNotFoundException;
import com.ga.commentapi.model.CommentModel;
import com.ga.commentapi.repository.CommentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommentServiceTest {
    private List<CommentModel> comments;
    private Iterable<CommentModel>empty = new ArrayList<>();


    @InjectMocks
    CommentServiceImpl commentService;

    @InjectMocks
    CommentModel comment;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Mock
    CommentRepository commentRepository;

    @Before
    @RabbitListener(queuesToDeclare = @Queue("Test-Queue"))
    public void init() {

        comment.setText("This is a comment from Farook");
        comment.setPostId(1L);
        comment.setUserId(1L);
        comment.setUsername("mike");

        comments = new ArrayList<CommentModel>();
        comments.add(comment);

    }

    @Test
    public void deleteCommentsByPostId_CommentList_Success()
    {
        when(commentRepository.deleteCommentsByPostId(anyLong())).thenReturn(comments);
        commentService.deleteCommentByPostId("deleteCommentByPostId:1");
        Assert.assertEquals(commentRepository.findAll(),empty);
    }

    @Test
    public void deleteComment_OK_SUCCESS(){
        HttpStatus test = commentService.deleteComment(1L);
        assertEquals(test.value(), HttpStatus.OK.value());
    }

    @Test
    public void getCommentsByPostId_CommentList_Success()
    {
        when(commentRepository.findCommentByPostId(anyLong())).thenReturn(comments);
        Iterable<CommentModel> postComments = commentService.getCommentsByPostId(comment.getPostId());
        assertEquals(postComments,comments);
    }

    @Test
    public void getCommentsByUsername_CommentList_Success()
    {
        when(commentRepository.findCommentsByUsername(anyString())).thenReturn(comments);
        Iterable<CommentModel> myComments = commentService.getCommentsByUsername(comment.getUsername());
        assertEquals(myComments,comments);
    }

    @Test
    public void createComment_CommentModel_SUCCESS() throws JsonProcessingException {
        comment.setNotifyOP(true);
        when(rabbitTemplate.convertSendAndReceive(anyString(),anyString())).thenReturn("OK");
        when(commentRepository.save(any())).thenReturn(comment);

        CommentModel testComment = commentService.createComment(comment.getPostId(), comment, "1", "user");

        assertEquals(testComment.getText(), comment.getText());
    }

    @Test(expected = PostNotFoundException.class)
    public void createComment__POST_NOT_FOUND_CommentModel_FAILURE() throws JsonProcessingException, PostNotFoundException {
        when(rabbitTemplate.convertSendAndReceive(anyString(),anyString())).thenReturn("NOT_FOUND");
        CommentModel testComment = commentService.createComment(comment.getPostId(), comment, "1", "user");
    }

}
