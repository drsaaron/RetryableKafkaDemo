/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class TriggerDataProcessImpl implements TriggerDataProcess {

    @Override
    public void processTrigger(TriggerData trigger, boolean doFail) {
        log.info("processing trigger {}", trigger);
        
        if (trigger.getId() % 4 == 0) {
            if (doFail) {
                log.info("throwing exception");
                throw new RuntimeException("intentional fail");
            } else {
                log.info("could fail but was asked not to");
            }
        }
    }
    
}
