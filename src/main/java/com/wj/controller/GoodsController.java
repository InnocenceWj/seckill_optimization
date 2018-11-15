package com.wj.controller;

import com.wj.const_wj.Const;
import com.wj.controller.baseController.BaseController;
import com.wj.entity.Good;
import com.wj.service.GoodServie;
import com.wj.service.SeckillGoodsService;
import com.wj.utils.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Controller
@RequestMapping(value = "/good")
public class GoodsController extends BaseController {

    @Resource
    private GoodServie goodServie;
    @Resource
    private SeckillGoodsService seckillGoodsServicedServie;

    @RequestMapping("/to_list")
    public ModelAndView toList() {
        ModelAndView mv = this.getModelAndView();
        List<PageData> goodList = goodServie.getListAll();
        mv.addObject("goodList", goodList);
        mv.setViewName("good/good_list");
        return mv;
    }

    @RequestMapping("/{id}/detail")
    public ModelAndView toDetail(@PathVariable(value = "id") Long seckillId, HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView();
        PageData pd=goodServie.findById(seckillId);
        String token= (String) request.getSession().getAttribute(Const.SESSION_USER);
        seckillGoodsServicedServie.createVerifyCode(token,seckillId);
        mv.addObject("good",pd);
        mv.addObject("token",token);
        mv.setViewName("good/good_detail");
        return mv;
    }

}
