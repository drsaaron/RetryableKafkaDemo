/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

/**
 *
 * @author aar1069
 */
@Slf4j
public class TriggerDataDeserializer implements Deserializer<TriggerData> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public TriggerData deserialize(String string, byte[] bytes) {
        try {
            String triggerJson = new String(bytes);
            log.info("extracting trigger from {}", triggerJson);
            return objectMapper.readValue(triggerJson, TriggerData.class);
        } catch(JsonProcessingException e) {
            throw new RuntimeException("error deserializing: " + e.getMessage(), e);
        }
    }
    
}
