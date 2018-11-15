package com.wj.controller;

import com.wj.const_wj.Const;
import com.wj.controller.baseController.BaseController;
import com.wj.result.CodeMsg;
import com.wj.result.Result;
import com.wj.service.SeckillGoodsService;
import com.wj.utils.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;

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

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public Result<PageData> time() {
        Date date = new Date();
        PageData pd = new PageData();
        pd.put("nowTime", date.getTime());
        return Result.success(pd);
    }


    @RequestMapping("/verifyCode")
    @ResponseBody
    public Result<String> getVerifyCode(HttpServletResponse response, HttpServletRequest request,
                                        @RequestParam("goodsId") long goodsId) {
        response.setContentType("application/json;charset=UTF-8");
        String token = (String) request.getSession().getAttribute(Const.SESSION_USER);
        BufferedImage image = seckillGoodsService.createVerifyCode(token, goodsId);
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }

    @RequestMapping(value = "/checkVerfyCode",method = RequestMethod.GET)
    @ResponseBody
    public Result<Integer> checkVerfyCode(HttpServletRequest request) {
        PageData pd=this.getPageData();
        String tokenS = (String) request.getSession().getAttribute(Const.SESSION_USER);
        String token = pd.getString("token");
        if (tokenS.equals(token)) {
            boolean flag = seckillGoodsService.checkVerfyCode(pd);
            return Result.success(0);
        } else {
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
    }
}
