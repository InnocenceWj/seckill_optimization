package com.wj.filter;

import com.wj.const_wj.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @创建人 wj
 * @创建时间 2018/11/15
 * @描述
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        if (path == null || path == "") {
            return false;
        } else if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
            return true;
        } else {
            String token = (String) request.getSession().getAttribute(Const.SESSION_USER);
            if (token == null || token == "") {
                System.out.println("AuthorizationInterceptor拦截请求");
                request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
            } else {
                //用户登陆过，验证通过，放行
                System.out.println("AuthorizationInterceptor放行请求");
                return true;
            }
        }
        return false;
    }

}
