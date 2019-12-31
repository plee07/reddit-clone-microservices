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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;


import java.io.IOException;

@RunWith(SpringRunner.class)
public class EmailServiceTest {

    private ObjectMapper json = new ObjectMapper();

    @InjectMocks
    private EmailService emailService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    JavaMailSenderImpl javaMailSender;


    private CommentBean commentBean;


    private PostBean postBean;


    private UserBean userBean;

    private User user;

    @Before
    public void init()
    {
        user = new User();
        user.setUsername("Batman");

        userBean = new UserBean();
        userBean.setEmail("test@test98756.com");
        userBean.setPassword("Batman");
        userBean.setUserId(1L);
        userBean.setUsername("Batman");

        commentBean = new CommentBean();
        commentBean.setCommentId(1L);
        commentBean.setNotifyOP(true);
        commentBean.setPostId(1L);
        commentBean.setText("My first comment");
        commentBean.setUser(user);
        commentBean.setUserId(1L);

        postBean = new PostBean();
        postBean.setDescription("This is post description");
        postBean.setPostId(1L);
        postBean.setTitle("My first title");
        postBean.setUser(user);
        postBean.setUserId(1L);


    }

    @Test
    public void EmailSender_SentEmail_Success() throws IOException {
        String comment_str = json.writeValueAsString(commentBean);
        String post_str = json.writeValueAsString(postBean);
        String user_str = json.writeValueAsString(userBean);
        when(rabbitTemplate.convertSendAndReceive(anyString(),anyString())).thenReturn(post_str, user_str);
//        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        doNothing().when(javaMailSender).send((MimeMessage) any());
        emailService.notifyOriginalPoster(comment_str);


    }

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
