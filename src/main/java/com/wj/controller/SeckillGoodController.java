package com.wj.controller;

import com.wj.controller.baseController.BaseController;
import com.wj.service.SeckillGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @创建人 wj
 * @创建时间 2018/11/14
 * @描述
 */
@Controller
@RequestMapping(value = "/seckill")
public class SeckillGoodController extends BaseController {
    @Resource
    private SeckillGoodsService seckillGoodsService;



}
