/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.blazartech.retryablekafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 *
 * @author aar1069
 */
@SpringBootApplication
@EnableKafka
public class RetryableKafkaDemo {

    public static void main(String[] args) {
        SpringApplication.run(RetryableKafkaDemo.class);
    }
}
