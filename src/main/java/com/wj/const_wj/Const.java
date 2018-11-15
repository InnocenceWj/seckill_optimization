package com.wj.const_wj;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public class Const {

    public static final String COOKIE_NAME_TOKEN = "token";
    public static final String SESSION_USER="sessionUser";
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(static)|(main)|(uploadFiles)).*";    //不对匹配该值的访问路径拦截（正则）
}
