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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommentServiceTest {
    private List<commentModel> comments;

    @InjectMocks
    CommentServiceImpl commentService;

    @InjectMocks
    commentModel comment;

    @Mock
    CommentRepository commentRepository;

    @Before
    public void init() {
        comment.setText("This is a comment from Farook");
        comment.setPostId(1L);
        comment.setUserId(1L);

        comments = new ArrayList<commentModel>();
        comments.add(comment);

    }

    @Test
    public void createComment_Comment_Success()
    {
        //InterServiceCall
    }

    @Test
    public void deleteCommentsByPostId_CommentList_Success()
    {
        when(commentRepository.deleteCommentsByPostId(anyLong())).thenReturn(comments);
        List<commentModel> deletedComments = comments;
        Assert.assertEquals(deletedComments,comments);
    }

    @Test
    public void getCommentsByPostId_CommentList_Success()
    {
        when(commentRepository.findCommentByPostId(anyLong())).thenReturn(comments);
        List<commentModel> postComments = comments;
        assertEquals(postComments,comments);
    }

    @Test
    public void getCommentsByUsername_CommentList_Success()
    {
        when(commentRepository.findCommentsByUsername(anyString())).thenReturn(comments);
        List<commentModel> myComments = comments;
        assertEquals(myComments,comments);
    }

}
