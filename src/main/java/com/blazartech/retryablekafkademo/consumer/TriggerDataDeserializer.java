/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

/**
 *
 * @author aar1069
 */
public class TriggerDataDeserializer implements Deserializer<TriggerData> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public TriggerData deserialize(String string, byte[] bytes) {
        try {
            return objectMapper.readValue(string, TriggerData.class);
        } catch(JsonProcessingException e) {
            throw new RuntimeException("error deserializing: " + e.getMessage(), e);
        }
    }
    
}
