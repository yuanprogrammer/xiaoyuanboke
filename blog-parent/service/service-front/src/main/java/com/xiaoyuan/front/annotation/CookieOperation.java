package com.xiaoyuan.front.annotation;


import java.lang.annotation.*;

/**
 * FileName:    WriteCookieToken
 * Author:      小袁
 * Date:        2022/5/3 16:57
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CookieOperation {

    boolean userToken() default false;

    boolean delUserToken() default false;

    boolean phonePermission() default false;

    boolean emailPermission() default false;
}
