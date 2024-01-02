/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.retryablekafkademo.consumer;

import com.blazartech.retryablekafkademo.TriggerData;

/**
 *
 * @author aar1069
 */
public interface TriggerDataProcess {
    
    public void processTrigger(TriggerData trigger, boolean doFail);
}
