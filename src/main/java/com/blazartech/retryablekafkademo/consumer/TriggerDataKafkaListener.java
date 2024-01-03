/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private TriggerDataProcess processor;

    @RetryableTopic(backoff = @Backoff(2_000), sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC, attempts = "10")
    @KafkaListener(topics = "${demo.topic}", containerFactory = "listenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
    public void processTriggerData(@Payload TriggerData trigger, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {

        log.info("got triggerJson: {} on topic {}", trigger, topic);

        boolean autoFail = true;

        if (topic.endsWith("retry-2")) {
            autoFail = false;
        }

        log.info("processing trigger {}", trigger);

        processor.processTrigger(trigger, autoFail);
    }
}
