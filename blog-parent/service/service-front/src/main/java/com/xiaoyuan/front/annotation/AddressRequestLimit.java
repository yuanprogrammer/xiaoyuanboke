package com.xiaoyuan.front.annotation;

import java.lang.annotation.*;

/**
 * FileName:    AddressRequestLimit
 * Author:      小袁
 * Date:        2022/4/29 22:45
 * Description: 限制IP请求上限
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AddressRequestLimit {

    long  seconds() default 6000; // 限制时间 单位：ms
    int maxCount() default 5; // 限制请求上限
}
