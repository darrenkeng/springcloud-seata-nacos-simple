package com.darrenkeng.springcloud.controller;

import com.darrenkeng.springcloud.service.AccountService;
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
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("debit")
    public void debit(String userId, int money) {
        accountService.debit(userId, money);
    }
}
