package com.darrenkeng.springcloud.service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-07
 */
public interface BusinessService {

    /**
     * 采购
     */
    void purchase(String userId, String commodityCode, int orderCount);

    /**
     * 采购
     */
    void purchaseWithoutTransactional(String userId, String commodityCode, int orderCount);
}
