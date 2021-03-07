package com.darrenkeng.springcloud.controller;

import com.darrenkeng.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@RestController
@Slf4j
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("deduct")
    public void deduct(String commodityCode, int count) {
        storageService.deduct(commodityCode, count);
    }
}
