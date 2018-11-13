package com.wj.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class SeckillGoods implements Serializable {
    private Long id;

    private Long goodsId;

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Date startTime;

    private Date endTime;

    private static final long serialVersionUID = 1L;

}