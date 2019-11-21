package com.ga.postapi.postapi.runner;

import com.ga.postapi.postapi.send.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

public class SenderRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Sender.class);
    @Autowired
    private ConfigurableApplicationContext ctx;
    @Value("${sender.client.duration:10000}")
    private int duration;
    @Override
    public void run(String... arg0) throws Exception {
        log.info("Ready ... sending for " + duration/1000 + " seconds");
        Thread.sleep(duration);
        ctx.close();
    }}