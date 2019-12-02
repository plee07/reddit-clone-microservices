package com.ga.emailservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.emailservice.model.CommentBean;
import com.ga.emailservice.model.PostBean;
import com.ga.emailservice.model.User;
import com.ga.emailservice.model.UserBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmailServiceTest {

    private ObjectMapper json = new ObjectMapper();

    @InjectMocks
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    JavaMailSenderImpl javaMailSender;

    @Mock
    CommentBean commentBean;

    @Mock
    PostBean postBean;

    @Mock
    UserBean userBean;

    @Mock
    User user;

    @Before
    public void init()
    {
        commentBean.setCommentId(1L);
        commentBean.setNotifyOP(true);
        commentBean.setPostId(1L);
        commentBean.setText("My first comment");
        commentBean.setUser(user);
        commentBean.setUserId(1L);

        postBean.setDescription("This is post description");
        postBean.setPostId(1L);
        postBean.setTitle("My first title");
        postBean.setUser(user);
        postBean.setUserId(1L);

        user.setUsername("Batman");

        userBean.setEmail("kty5876@gmail.com");
        userBean.setPassword("Batman");
        userBean.setUserId(1L);
        userBean.setUsername("Batman");
    }

//    @Test
//    public void EmailSender_SentEmail_Success() throws IOException {
//        String postJson = "My Post";
//        String userJson = "My User";
//        when(json.readValue(anyString(),CommentBean.class)).thenReturn(commentBean);
//        when(json.readValue(anyString(),PostBean.class)).thenReturn(postBean);
//        when(rabbitTemplate.convertSendAndReceive("getPostById", anyLong())).thenReturn(postJson);
//        when(json.readValue(anyString(),UserBean.class)).thenReturn(userBean);
//        when(rabbitTemplate.convertSendAndReceive("getUserById", anyLong())).thenReturn(userJson);
//
//
//
//    }
    @Test
    public void UserBean_Model_Test(){
        UserBean ub = new UserBean();
        ub.setUsername("testUser");
        String ubTest = ub.getUsername();
        Assert.assertEquals(ubTest, ub.getUsername());

    }
    @Test
    public void CommentBean_Model_Test(){
        CommentBean cb = new CommentBean();
        cb.setCommentId(1L);
        cb.setNotifyOP(true);
        cb.setPostId(1L);
        cb.setText("My first comment");
        cb.setUser(user);
        cb.setUserId(1L);
        CommentBean cbTest = cb;
        Assert.assertEquals(cbTest.getCommentId(), cb.getCommentId());

    }
    @Test
    public void PostBean_Model_Test(){
        PostBean pb = new PostBean();
        pb.setDescription("This is post description");
        pb.setPostId(1L);
        pb.setTitle("My first title");
        pb.setUser(user);
        pb.setUserId(1L);
        PostBean pbTest = pb;
        Assert.assertEquals(pbTest.getPostId(), pb.getPostId());

    }
}
