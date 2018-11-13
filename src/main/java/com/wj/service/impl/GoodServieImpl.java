package com.wj.service.impl;

import com.wj.mapper.GoodMapper;
import com.wj.service.GoodServie;
import com.wj.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Service
public class GoodServieImpl implements GoodServie {
    @Resource
    private GoodMapper goodMapper;

    @Override
    public List<PageData> getListAll() {
        return goodMapper.getListAll();
    }
}
