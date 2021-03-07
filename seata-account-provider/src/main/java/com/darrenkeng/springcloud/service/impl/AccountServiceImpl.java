package com.darrenkeng.springcloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darrenkeng.springcloud.entities.Account;
import com.darrenkeng.springcloud.exception.BusinessException;
import com.darrenkeng.springcloud.mapper.AccountMapper;
import com.darrenkeng.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void debit(String userId, int money) {
        Account account = new Account();
        account.setUserId(userId);

        Account existAccount = accountMapper.selectOne(Wrappers.query(account));
        if (Objects.isNull(existAccount)) {
            log.error("userId:{} record not exist", userId);
            throw new BusinessException(404, "record not exist");
        }

        if (existAccount.getMoney() - money < 0) {
            log.error("userId:{} Insufficient account balance", userId);
            throw new BusinessException(403, "Insufficient account balance");
        }
        existAccount.setMoney(existAccount.getMoney() - money);

        accountMapper.updateById(existAccount);
    }
}
