package com.xiaoyuan.back.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaoyuan.common_util.annotation.IsAdmin;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.lang.reflect.Field;

public class IsAdminResolver implements HandlerMethodArgumentResolver {

    private final RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    public IsAdminResolver(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(IsAdmin.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, @NonNull NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object param = this.requestResponseBodyMethodProcessor.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        if (param != null) {
            Field field = param.getClass().getDeclaredField("isAdmin");
            // 暴力反射
            field.setAccessible(true);
            // 判断当前用户是不是管理员
            if (StpUtil.hasRole("ADMIN")) {
                field.set(param, "true");
            }else {
                field.set(param, StpUtil.getLoginIdAsString());
            }
        }
        return param;
    }
}
