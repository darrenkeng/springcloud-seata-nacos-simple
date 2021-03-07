package com.darrenkeng.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-07
 */
@FeignClient("seata-order-provider")
public interface OrderService {

    @GetMapping("create")
    void create(@RequestParam("userId") String userId,
                @RequestParam("commodityCode") String commodityCode,
                @RequestParam("orderCount") int orderCount);
}
