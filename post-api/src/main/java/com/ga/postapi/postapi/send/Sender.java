package com.ga.postapi.postapi.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Sender {
    private static final Logger log = LoggerFactory.getLogger(Sender.class);
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private Queue queue;
    @Scheduled(fixedRateString = "${sender.client.fixedRate}")
    public void send() {
        String message = "Message (" + DateTimeFormatter.ISO_LOCAL_TIME.format(LocalDateTime.now()) + ")";
        this.template.convertAndSend(queue.getName(), message);
        log.info(" [x] Sent '{}'", message);
    }
}