package com.xiaoyuan.back.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.xiaoyuan.back.handler.IsAdminResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * FileName:    WebMVCConfig
 * Author:      小袁
 * Date:        2022/5/6 9:47
 * Description:
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private IsAdminResolver isAdminResolver;

    // 实例化属性
    @Bean(value = "requestResponseBodyMethodProcessor")
    public RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> httpMessageConverters) {
        return new RequestResponseBodyMethodProcessor(httpMessageConverters);
    }

    // 实例化IsAdmin处理着
    @Bean
    public IsAdminResolver isAdminResolver(@Qualifier("requestResponseBodyMethodProcessor") RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor){
        return new IsAdminResolver(requestResponseBodyMethodProcessor);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**").excludePathPatterns("/webSocket/**");
    }

    // 开启参数解析
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(isAdminResolver);
    }
}
