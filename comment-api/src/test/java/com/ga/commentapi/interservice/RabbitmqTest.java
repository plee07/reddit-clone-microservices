//package com.ga.commentapi.interservice;
//
//import com.ga.commentapi.model.UserBean;
//import com.ga.commentapi.model.commentModel;
//import com.ga.commentapi.repository.CommentRepository;
//import com.google.common.io.Files;
//import org.apache.qpid.server.Broker;
//import org.apache.qpid.server.BrokerOptions;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.rules.ExternalResource;
//import org.junit.runner.RunWith;
//import org.junit.runner.Runner;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Properties;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest("scanBasePackages")
//public class RabbitmqTest {
//
//    @Value("${spring.rabbitmq.port}")
//    private String rabbitmqPort;
//    private static commentModel comment = new commentModel();
//
//
//    public static final String QPID_CONFIG_LOCATION = "src/test/resources/qpid-config.json";
//    public static final String APPLICATION_CONFIG_LOCATION = "src/test/resources/application.properties";
//
//    @MockBean
//    private Runner runner;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private Reciever reciever;
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @ClassRule
//    public static final ExternalResource resource = new ExternalResource() {
//        private Broker broker = new Broker();
//
//        @Override
//        protected void before() throws Throwable {
//            Properties properties = new Properties();
//            properties.load(new FileInputStream(new File(APPLICATION_CONFIG_LOCATION)));
//            String amqpPort = properties.getProperty("spring.rabbitmq.port");
//            File tmpFolder = Files.createTempDir();
//            String userDir = System.getProperty("user.dir").toString();
//            File file = new File(userDir);
//            String homePath = file.getAbsolutePath();
//            BrokerOptions brokerOptions = new BrokerOptions();
//            brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.getAbsolutePath());
//            brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort);
//            brokerOptions.setConfigProperty("qpid.home_dir", homePath);
//            brokerOptions.setInitialConfigurationLocation(homePath + "/" + QPID_CONFIG_LOCATION);
//            broker.startup(brokerOptions);
//            comment = new commentModel();
//            comment.setUsername("Batman");
//            comment.setPostId(1L);
//            comment.setUserId(1L);
//            comment.setUserId(1L);
//            comment.setText("I am the night");
//            comment.setCommentId(1L);
//        }
//
//
//        @Override
//        protected void after() {
//            broker.shutdown();
//        }
//
//    };
//
//
//    @Test
//    public void testWithFirstReceiverRoutingKey() throws Exception {
//        reciever.initCounter();
//        rabbitTemplate.convertAndSend("TestQueue", "Hello from RabbitMQ Sent 1!");
//        rabbitTemplate.convertAndSend("TestQueue", "Hello from RabbitMQ Sent 2!");
//        Thread.sleep(5000);
//        assertThat(reciever.getCounter()).isEqualTo(2);
//    }
//
//    public commentModel createCommentTest(Long postId, commentModel comment, String id, String username)
//    {
//        //when(rabbitTemplate.convertSendAndReceive("TestQueue",message)).thenReturn("1");
//        String message = "checkPostId:" + postId;
//        String postfound = (String) rabbitTemplate.convertSendAndReceive("Broker",message);
//        comment.setPostId(postId);
//        comment.setUserId(Long.parseLong(id));
//        comment.setUsername(username);
//        comment.setUser(new UserBean(username));
//        System.out.println(postfound);
//        return commentRepository.save(comment);
//    }
//    @Test
//    public void createCommentSuccess()
//    {
//        commentModel mycomment = createCommentTest(comment.getPostId(),comment,comment.getUserId().toString(),comment.getUsername());
//        System.out.println("reach");
//        System.out.println(mycomment.getText());
//        Assert.assertEquals(mycomment.getText(),comment.getText());
//    }
//}

