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
@FeignClient("seata-account-provider")
public interface AccountService {

    @GetMapping("debit")
    void debit(@RequestParam("userId") String userId, @RequestParam("money") int money);
}
