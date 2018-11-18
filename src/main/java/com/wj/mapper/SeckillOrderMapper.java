package com.wj.mapper;

import com.wj.entity.SeckillOrder;
import com.wj.utils.PageData;

public interface SeckillOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrder record);

    int insertSelective(SeckillOrder record);

    SeckillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillOrder record);

    int updateByPrimaryKey(SeckillOrder record);

    PageData findByGoodIdAndUserId(PageData pd);

    void insertOrder(PageData pd);
}