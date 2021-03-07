package com.darrenkeng.springcloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darrenkeng.springcloud.entities.Storage;
import com.darrenkeng.springcloud.exception.BusinessException;
import com.darrenkeng.springcloud.mapper.StorageMapper;
import com.darrenkeng.springcloud.service.StorageService;
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
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void deduct(String commodityCode, int count) {
        Storage storage = new Storage();
        storage.setCommodityCode(commodityCode);
        Storage existStorage = storageMapper.selectOne(Wrappers.query(storage));

        if (Objects.isNull(existStorage)) {
            log.error("commodityCode:{} record not exist", commodityCode);
            throw new BusinessException(404, "record not exist");
        }

        if (existStorage.getCount() - count < 0) {
            log.error("commodityCode:{} Out of stock", commodityCode);
            throw new BusinessException(403, "Out of stock");
        }

        existStorage.setCount(existStorage.getCount() - count);
        storageMapper.updateById(existStorage);
    }
}
