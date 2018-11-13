package com.wj.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class SeckillOrder implements Serializable {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long orderId;

    private static final long serialVersionUID = 1L;

}