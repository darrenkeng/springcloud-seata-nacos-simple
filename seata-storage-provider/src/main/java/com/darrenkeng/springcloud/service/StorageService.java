package com.darrenkeng.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.darrenkeng.springcloud.entities.Storage;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Service
public interface StorageService extends IService<Storage> {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
