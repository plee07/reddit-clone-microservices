//package com.ga.commentapi.interservice;
//
//import com.google.common.io.Files;
//import org.apache.qpid.server.Broker;
//import org.apache.qpid.server.BrokerOptions;
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
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RabbitmqTest {
//
//    @Value("${spring.rabbitmq.port}")
//    private String rabbitmqPort;
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
//
//}