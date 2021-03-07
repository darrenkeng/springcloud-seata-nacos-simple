package com.darrenkeng.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darrenkeng.springcloud.entities.Account;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Service
public interface AccountService extends IService<Account> {

    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
