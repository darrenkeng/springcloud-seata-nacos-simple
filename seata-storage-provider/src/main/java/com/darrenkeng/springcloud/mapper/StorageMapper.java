package com.darrenkeng.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.darrenkeng.springcloud.entities.Storage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
}
