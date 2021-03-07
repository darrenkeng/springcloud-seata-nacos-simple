package com.darrenkeng.springcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darrenkeng.springcloud.entities.Order;
import com.darrenkeng.springcloud.mapper.OrderMapper;
import com.darrenkeng.springcloud.service.AccountService;
import com.darrenkeng.springcloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {

        int orderMoney = calculate(commodityCode, orderCount);

        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.setId(null);
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        // INSERT INTO orders ...
        orderMapper.insert(order);

        return order;
    }

    private int calculate(String commodityCode, int orderCount) {
        switch (commodityCode) {
            case "v1":
                return 1 * orderCount;
            case "v2":
                return 10 * orderCount;
            default:
                return orderCount;
        }
    }
}
