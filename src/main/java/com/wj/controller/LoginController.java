package com.wj.controller;

import com.wj.controller.baseController.BaseController;
import com.wj.result.Result;
import com.wj.service.SeckillUserService;
import com.wj.utils.MD5Util;
import com.wj.utils.PageData;
import com.wj.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述 登录
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController{

    @Resource
    private SeckillUserService seckillUserService;

    @RequestMapping(value = "/to_register")
    public String register() {
        return "register/register";
    }

    @RequestMapping(value = "/do_register")
    @ResponseBody
    public Result<PageData> doRegister(){
        PageData pd=this.getPageData();
        pd.put("id", UUIDUtil.getGuid());
        pd.put("registerDate", new Date());
        pd.put("nickname", pd.getString("mobile"));
        pd.put("password", MD5Util.inputPassToDBPass(pd.getString("password"), pd.getString("mobile")));
        seckillUserService.doRegister(pd);
        return Result.success(pd);
    }

    @RequestMapping(value = "/to_login")
    public String toLogin() {
        return "login/login";
    }


    @RequestMapping(value = "/do_login")
    @ResponseBody
    public Result<PageData> doLogin(HttpServletResponse response) {
        PageData pd=this.getPageData();
        //登录
        String token = seckillUserService.login(response, pd);
        pd.put("token",token);
        return Result.success(pd);
    }


}
