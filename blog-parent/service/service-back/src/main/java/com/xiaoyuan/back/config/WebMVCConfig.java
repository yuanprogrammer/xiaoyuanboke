package com.xiaoyuan.back.config;

import com.xiaoyuan.back.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileName:    WebMVCConfig
 * Author:      小袁
 * Date:        2022/5/6 9:47
 * Description:
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有接口, 验证权限
        registry.addInterceptor(new PermissionInterceptor())
                .addPathPatterns("/**");
    }
}
