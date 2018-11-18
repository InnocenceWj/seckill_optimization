package com.wj.mapper;

import com.wj.entity.SeckillGoods;
import com.wj.utils.PageData;

public interface SeckillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    SeckillGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);

    PageData getStockById(long goodId);

    boolean reduceStock(PageData pd);
}