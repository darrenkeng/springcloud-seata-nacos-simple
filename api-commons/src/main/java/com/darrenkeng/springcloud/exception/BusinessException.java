package com.darrenkeng.springcloud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @author: darren
 * @company: wonhigh
 * @create: 2021-03-07
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;
}
