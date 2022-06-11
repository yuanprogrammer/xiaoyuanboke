package com.xiaoyuan.front.aspect;

import com.xiaoyuan.front.annotation.CookieOperation;
import com.xiaoyuan.utils.constant.CookieConstant;
import com.xiaoyuan.front.utils.StringThreadLocal;
import com.xiaoyuan.utils.constant.RedisConstantKey;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * FileName:    ResponseCookieAspect
 * Author:      小袁
 * Date:        2022/5/3 17:03
 * Description:
 */
@Aspect
@Component
public class ResponseCookieAspect {

    @Pointcut("@annotation(cookieOperation)")
    public void excludeService(CookieOperation cookieOperation) {}

    @AfterReturning("excludeService(cookieOperation)")
    public void WriteResponseCookie(JoinPoint joinPoint, CookieOperation cookieOperation) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(attributes).getResponse();

        // 用户Token
        if (cookieOperation.userToken()) {
            // 设置Token
            String token = StringThreadLocal.get();
            if (!StringUtils.isBlank(token) && response != null) {
                Cookie cookie = new Cookie(CookieConstant.TOKEN, token);
                cookie.setPath("/");
                cookie.setMaxAge(CookieConstant.EXPIRE);
                cookie.setHttpOnly(true);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                // 写入Cookie到response
                response.addCookie(cookie);
            }
            return;
        }

        if (cookieOperation.delUserToken()){
            if (response != null) {
                // 清除Token
                Cookie cookie = new Cookie(CookieConstant.TOKEN, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return;
        }

        // 号码权限码
        if (cookieOperation.phonePermission()) {
            String permissionCode = StringThreadLocal.get();
            if (permissionCode != null && response != null) {
                Cookie cookie = new Cookie(RedisConstantKey.MOBILE_NUMBER_REQUEST_VERIFY, permissionCode);
                cookie.setPath("/");
                cookie.setMaxAge(RedisConstantKey.EXPIRE_TEN_SECOND);
                cookie.setHttpOnly(true);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.addCookie(cookie);
            }
            return;
        }

        // 邮箱权限码
        if (cookieOperation.emailPermission()) {
            String emailPermissionCode = StringThreadLocal.get();
            if (emailPermissionCode != null && response != null) {
                Cookie cookie = new Cookie(RedisConstantKey.EMAIL_REQUEST_VERIFY, emailPermissionCode);
                cookie.setPath("/");
                cookie.setMaxAge(RedisConstantKey.EXPIRE_TEN_SECOND);
                cookie.setHttpOnly(true);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.addCookie(cookie);
            }
        }
    }
}
