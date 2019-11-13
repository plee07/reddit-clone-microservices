//package com.ga.postapi.postapi;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * Send a message to the queue and display a message in the console when sent.
// *
// * This lab is based on <a href="https://www.rabbitmq.com/tutorials/tutorial-one-java.html">RabbitMQ Tutorial One</a>.
// */
//public class Send {
//
//    // The name of the queue to send messages to
//    private final static String QUEUE_NAME = "queue1";
//
//    public static void main(String[] argv) throws Exception {
//
//        // Set up a connection to the queue on localhost
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        // try-with-resources to connect
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()) {
//
//            // Declaring a queue is idempotent - it will only be created if it doesn't exist already.
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//            // send the message
//            String message = "Hello World!";
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
//
//            // inform the user via the console
//            System.out.println(" [x] Sent '" + message + "'");
//        }
//    }
//}
