package com.xiaoyuan.front.interceptor;

import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.utils.StringThreadLocal;
import com.xiaoyuan.model.constants.CookieConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    LoginInterceptor
 * Author:      小袁
 * Date:        2022/5/3 11:50
 * Description: 用户登录注册拦截
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行静态资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取cookie
        Cookie[] cookies = request.getCookies();

        String token = null;
        if (cookies != null && cookies.length != 0) {
            // 遍历, 查找token
            for (Cookie cookie : cookies) {
                if (CookieConstant.TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }else {
            // 未登录, 放行
            return true;
        }

        // token过期失效, 不存在, 放行去登录
        if (StringUtils.isBlank(token)) return true;
        if (tokenService.checkToken(token) == null) return true;

        // 已登录过, 重定向首页
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect(request.getContextPath() + "/home/home");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (StringThreadLocal.get() != null) {
            StringThreadLocal.remove();
        }
    }
}
