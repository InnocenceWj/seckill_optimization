package com.wj.service;

import com.wj.utils.PageData;

import javax.servlet.http.HttpServletResponse; /**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public interface SeckillUserService {
    void doRegister(PageData pd);

    String login(HttpServletResponse response, PageData pd);
}
