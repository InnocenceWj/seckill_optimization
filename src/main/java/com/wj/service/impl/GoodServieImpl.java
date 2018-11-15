package com.wj.service.impl;

import com.wj.mapper.GoodMapper;
import com.wj.redis.GoodKey;
import com.wj.redis.RedisDao;
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

    //    别忘了去spring-redis.xml配置<context:component-scan base-package="com.wj.redis" />，否则无法注入RedisDao
    @Resource
    private RedisDao redisDao;
    @Resource
    private GoodMapper goodMapper;

    @Override
    public List<PageData> getListAll() {
        List<PageData> list = redisDao.getList(GoodKey.goodsList.getPrefix());
        if (null == list || list.isEmpty()) {
            list = goodMapper.getListAll();
            redisDao.setList(GoodKey.goodsList, list);
        }
        return list;
    }

    @Override
    public PageData findById(long id) {
        PageData pd = redisDao.getObj(GoodKey.goodsDetail.getPrefix(), PageData.class);
        if(null==pd){
            pd = goodMapper.selectByPrimaryKey(id);
            redisDao.setObj(GoodKey.goodsDetail.getPrefix(),pd,GoodKey.goodsDetail.expireSeconds());
        }
        return pd;
    }
}
