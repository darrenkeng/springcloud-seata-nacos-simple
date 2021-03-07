package com.darrenkeng.springcloud.controller;

import com.darrenkeng.springcloud.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-07
 */
@RestController
@Slf4j
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("testSuccess")
    public void testSuccess() {
        businessService.purchase("zhangsan", "v1", 10);
    }

    @GetMapping("testFailure")
    public void testFailure() {
        businessService.purchase("zhangsan", "v2", 10);
    }

    @GetMapping("testFailureWithoutTransactional")
    public void testFailureWithoutTransactional() {
        businessService.purchaseWithoutTransactional("zhangsan", "v2", 10);
    }
}
