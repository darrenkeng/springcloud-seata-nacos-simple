package com.darrenkeng.springcloud.service.impl;

import com.darrenkeng.springcloud.service.BusinessService;
import com.darrenkeng.springcloud.service.OrderService;
import com.darrenkeng.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-07
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private OrderService orderService;

    /**
     * 采购 - 分布式事务
     */
    @Override
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }

    /**
     * 采购 - 无事务
     */
    @Override
    public void purchaseWithoutTransactional(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }
}
