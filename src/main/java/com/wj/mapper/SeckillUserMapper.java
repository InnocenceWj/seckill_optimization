package com.wj.mapper;


import com.wj.entity.SeckillUser;
import com.wj.utils.PageData;

public interface SeckillUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PageData record);

    int insertSelective(PageData record);

    PageData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PageData record);

    int updateByPrimaryKey(PageData record);

    SeckillUser findByMobile(PageData pd);
}