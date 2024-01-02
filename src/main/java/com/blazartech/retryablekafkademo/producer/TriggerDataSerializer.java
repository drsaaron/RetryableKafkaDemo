/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

/**
 *
 * @author aar1069
 */
public class TriggerDataSerializer implements Serializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public byte[] serialize(String string, Object t) {
        try {
            return objectMapper.writeValueAsBytes(t);
        } catch(JsonProcessingException e) {
            throw new RuntimeException("error serializing: " + e.getMessage(), e);
        }
    }
    
}
