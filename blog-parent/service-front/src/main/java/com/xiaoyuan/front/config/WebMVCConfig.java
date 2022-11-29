package com.xiaoyuan.front.config;

import com.xiaoyuan.front.interceptor.ErrorInterceptor;
import com.xiaoyuan.front.interceptor.LoginInterceptor;
import com.xiaoyuan.front.interceptor.UserPermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileName:    WebMVCConfig
 * Author:      小袁
 * Date:        2022/1/22 18:45
 * Description:
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private UserPermissionInterceptor userPermissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");

        // 拦截用户已经登录过再打开登录注册界面
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/login")
                .addPathPatterns("/user/login");

        // 权限拦截
        registry.addInterceptor(userPermissionInterceptor)
                .addPathPatterns("/homeMessage/insert")
                .addPathPatterns("/article/comment")
                .addPathPatterns("/user/password/modify")
                .addPathPatterns("/user/nickname/modify")
                .addPathPatterns("/article/like")
                .addPathPatterns("/article/collect");
    }
}
