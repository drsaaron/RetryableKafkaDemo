/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.producer;

import com.blazartech.retryablekafkademo.TriggerData;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class SendMessagesCommandLineRunner implements CommandLineRunner {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    
    @Value("${demo.topic}")
    private String topicName;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("sending messages");
        
        List<TriggerData> triggers = List.of(
                new TriggerData(1, "person1"), 
                new TriggerData(2, "person2"),
                new TriggerData(3, "person3"),
                new TriggerData(4, "person4"),
                new TriggerData(5, "person5")
        );
        triggers.forEach(t -> kafkaTemplate.send(topicName, t));
    }
    
}
