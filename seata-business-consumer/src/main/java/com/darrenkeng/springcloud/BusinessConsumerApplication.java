package com.darrenkeng.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BusinessConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessConsumerApplication.class, args);
    }
}
