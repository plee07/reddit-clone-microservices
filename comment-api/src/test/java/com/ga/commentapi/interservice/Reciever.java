package com.ga.commentapi.interservice;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Reciever {

  private int count = 0;

    @RabbitListener(queuesToDeclare = @Queue("TestQueue"))
    public void reciver(String message)
    {
        System.out.println("The message is " + message);
        count++;
    }

    public Integer getCounter() {
        return count;
    }

    public void initCounter() {
        this.count = 0;
    }
}
