package com.darrenkeng.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("storage_tbl")
public class Storage {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String commodityCode;

    private Integer count;

}
