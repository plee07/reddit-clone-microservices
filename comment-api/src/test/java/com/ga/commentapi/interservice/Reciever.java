//package com.ga.commentapi.interservice;
//
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Reciever {
//
//  private int count = 0;
//
//    @RabbitListener(queuesToDeclare = @Queue("TestQueue"))
//    public void reciver(String message)
//    {
//        System.out.println("The message is " + message);
//        count++;
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue("Broker"))
//    public String brokerTest(String message)
//    {
//        return "Post Found";
//    }
//
//    public Integer getCounter() {
//        return count;
//    }
//
//    public void initCounter() {
//        this.count = 0;
//    }
//}
