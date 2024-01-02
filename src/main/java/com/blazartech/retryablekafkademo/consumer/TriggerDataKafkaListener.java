/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class TriggerDataKafkaListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TriggerDataProcess processor;
    
    private String transformString(String s) {
        return s.translateEscapes().replaceAll("^\"", "").replaceAll("\"$", "");
    }
    
    private String extractTrigger(String s) {
        while (true) {
            String updatedString = transformString(s);
            if (updatedString.equals(s)) {
                return updatedString;
            } else {
                s = updatedString;
            }
        }
    }

    @RetryableTopic(backoff = @Backoff(2_000), sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC, attempts = "10")
    @KafkaListener(topics = "${demo.topic}")
    public void processTriggerData(@Payload String triggerJson, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {

        log.info("got triggerJson: {} on topic {}", triggerJson, topic);
        
        boolean autoFail = true;
        
        if (topic.endsWith("-retry")) {
//        if (topic.matches("^.*retry-[0-9]")) {
            triggerJson = extractTrigger(triggerJson);
            log.info("transformed trigger = {}", triggerJson);
            
            if (topic.endsWith("retry-2")) {
                autoFail = false;
            }
        }
        
        TriggerData trigger = objectMapper.readValue(triggerJson, TriggerData.class);
        log.info("processing trigger {}", trigger);

        processor.processTrigger(trigger, autoFail);
    }
}
