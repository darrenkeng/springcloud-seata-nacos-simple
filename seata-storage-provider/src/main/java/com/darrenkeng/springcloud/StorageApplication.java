package com.darrenkeng.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.darrenkeng.springcloud.mapper")
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
