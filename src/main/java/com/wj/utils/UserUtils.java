package com.wj.utils;

import com.alibaba.fastjson.JSON;
import com.wj.const_wj.Const;
import com.wj.entity.SeckillUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @创建人 wj
 * @创建时间 2018/11/16
 * @描述
 */
public class UserUtils {
    private static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 从session中获取用户
     *
     * @return user
     */
    public static SeckillUser getUser() {
        return (SeckillUser) getSession().getAttribute(Const.SESSION_USER);
    }

    public static void setUser(SeckillUser user) {
        getSession().setAttribute(Const.SESSION_USER, user);
    }

    public static void removeUser() {
        HttpSession session = getSession();
        session.removeAttribute(Const.SESSION_USER);
        session.invalidate();
    }

    /**
     * 从session中获取token
     *
     * @return user
     */
    public static String getToken() {
        return (String) getSession().getAttribute(Const.COOKIE_NAME_TOKEN);
    }

    public static void setToken(String token) {
        getSession().setAttribute(Const.COOKIE_NAME_TOKEN, token);
    }

    public static void removeToken() {
        HttpSession session = getSession();
        session.removeAttribute(Const.COOKIE_NAME_TOKEN);
        session.invalidate();
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }


}
