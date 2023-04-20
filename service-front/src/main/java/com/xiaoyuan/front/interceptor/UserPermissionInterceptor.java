package com.xiaoyuan.front.interceptor;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.model.constants.CookieConstant;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    UserPermissionInterceptor
 * Author:      小袁
 * Date:        2022/5/3 9:47
 * Description:
 */
@Component
@Slf4j
public class UserPermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    // 执行controller前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果访问的是静态资源, 放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        response.setContentType("application/json;charset=utf-8");

        Cookie[] cookies = request.getCookies();

        // 未登录, 拒绝
        if (cookies == null || cookies.length == 0) {
            response.getWriter().println(JSON.toJSONString(R.fail(HttpStatusEnum.USER_NO_LOGIN)));
            return false;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (CookieConstant.TOKEN.equals(cookie.getName())) {
                // token非法, 拒绝
                token = cookie.getValue();
                if (StringUtils.isBlank(token)) {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().println(JSON.toJSONString(R.fail(HttpStatusEnum.USER_NO_LOGIN)));
                    return false;
                }
            }
        }

        CommonUserVo commonUserVo = tokenService.checkToken(token);

        // token失效
        if (commonUserVo == null) {
            response.getWriter().println(JSON.toJSONString(R.fail(HttpStatusEnum.TOKEN_INVALID)));
            return false;
        }

        // 验证全部通过, 回传数据
        UserThreadLocal.put(commonUserVo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 执行结束时关闭本地线程, 避免内存泄漏
        if (UserThreadLocal.get() != null) {
            UserThreadLocal.remove();
        }
    }
}
