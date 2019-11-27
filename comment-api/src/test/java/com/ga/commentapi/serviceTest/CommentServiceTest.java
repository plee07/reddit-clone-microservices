package com.ga.commentapi.serviceTest;

import com.ga.commentapi.model.commentModel;
import com.ga.commentapi.repository.CommentRepository;
import com.ga.commentapi.service.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommentServiceTest {
    private List<commentModel> comments;
    private Iterable<commentModel>empty = new ArrayList<>();


    @InjectMocks
    CommentServiceImpl commentService;

    @InjectMocks
    commentModel comment;

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

        comments = new ArrayList<commentModel>();
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
    public void getCommentsByPostId_CommentList_Success()
    {
        when(commentRepository.findCommentByPostId(anyLong())).thenReturn(comments);
        Iterable<commentModel> postComments = commentService.getCommentsByPostId(comment.getPostId());
        assertEquals(postComments,comments);
    }

    @Test
    public void getCommentsByUsername_CommentList_Success()
    {
        when(commentRepository.findCommentsByUsername(anyString())).thenReturn(comments);
        Iterable<commentModel> myComments = commentService.getCommentsByUsername(comment.getUsername());
        assertEquals(myComments,comments);
    }

}
