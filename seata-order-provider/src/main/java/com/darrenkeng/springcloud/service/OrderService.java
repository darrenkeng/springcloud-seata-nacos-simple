package com.darrenkeng.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darrenkeng.springcloud.entities.Order;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Service
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     */
    Order create(String userId, String commodityCode, int orderCount);
}
