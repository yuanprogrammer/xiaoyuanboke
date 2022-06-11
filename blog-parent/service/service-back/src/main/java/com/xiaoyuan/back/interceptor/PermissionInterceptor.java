package com.xiaoyuan.back.interceptor;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    PermissionInterceptor
 * Author:      小袁
 * Date:        2022/5/6 9:42
 * Description:
 */
public class PermissionInterceptor implements HandlerInterceptor {

    private final static String password = "xiaoyuanboke" + "xiaoyuan@@#!$";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(R.error(HttpStatusEnum.ILLEGAL_OPERATION)));
            return false;
        }
        else {
            if (token.equals(DigestUtils.md5Hex(password))) {
                return true;
            }else {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(JSON.toJSONString(R.error(HttpStatusEnum.ILLEGAL_OPERATION)));
                return false;
            }
        }
    }
}
