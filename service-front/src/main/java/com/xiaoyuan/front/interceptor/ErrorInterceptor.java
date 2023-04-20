package com.xiaoyuan.front.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    ErrorInterceptor
 * Author:      小袁
 * Date:        2022/1/22 18:41
 * Description:
 */
// 404,500错误拦截
public class ErrorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (response.getStatus() == 404) {
            ModelAndView view = new ModelAndView("error/404");
            modelAndView = view;
        }else if (response.getStatus() == 500) {
            ModelAndView view = new ModelAndView("error/500");
            modelAndView = view;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
