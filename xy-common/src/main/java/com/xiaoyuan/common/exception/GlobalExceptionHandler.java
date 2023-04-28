package com.xiaoyuan.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.xiaoyuan.common.enums.HttpStatusEnum;
import com.xiaoyuan.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public R NotLoginExceptionHandler(NotLoginException e) {
        return R.fail(HttpStatusEnum.TOKEN_INVALID);
    }

    /**
     * 权限
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public R NotPermissionExceptionHandler(NotPermissionException e) {
        return R.fail(HttpStatusEnum.NOT_PERMISSION);
    }

    /**
     * Valid校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return R.fail(HttpStatusEnum.PARAM_ERROR);
    }
}
