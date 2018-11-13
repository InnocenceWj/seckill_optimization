package com.wj.service.impl;

import com.wj.entity.SeckillUser;
import com.wj.exception.GlobalException;
import com.wj.mapper.SeckillUserMapper;
import com.wj.result.CodeMsg;
import com.wj.service.SeckillUserService;
import com.wj.utils.MD5Util;
import com.wj.utils.PageData;
import com.wj.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.wj.utils.CookieUtil.addCookie;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Service
public class SeckillUserServiceImpl implements SeckillUserService {


    @Resource
    private SeckillUserMapper seckillUserMapper;
    @Override
    public void doRegister(PageData pd) {
        seckillUserMapper.insert(pd);
    }

    @Override
    public String login(HttpServletResponse response, PageData pd) {
        if (pd == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = pd.getString("mobile");
        String formPass=pd.getString("password");

        //判断手机号是否存在
        SeckillUser user = seckillUserMapper.findByMobile(pd);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getNickname();
        String calcPass = MD5Util.FormPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        //生成cookie
        addCookie(response, token, user);
        return token;
    }
}
