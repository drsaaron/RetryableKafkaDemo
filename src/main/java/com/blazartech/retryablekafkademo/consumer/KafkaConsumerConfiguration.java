/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;

/**
 *
 * @author aar1069
 */
@Configuration
public class KafkaConsumerConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    
    @Bean
    public ConsumerFactory<Integer, TriggerData> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configProps.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                TriggerDataDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }
    
    @Bean
    public KafkaListenerContainerFactory listenerContainerFactory(ConsumerFactory<Integer, TriggerData> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, TriggerData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    
    // see https://docs.spring.io/spring-kafka/reference/retrytopic/topic-naming.html#single-topic-fixed-delay
    @Bean
    public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, TriggerData> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .fixedBackOff(3_000)
                .maxAttempts(5)
                .useSingleTopicForSameIntervals()
                .create(template);
    }
}
