package com.xiaoyuan.service.exception;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.service.utils.ExceptionUtil;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * FileName:    GlobalExceptionHandler
 * Author:      小袁
 * Date:        2022/5/9 17:31
 * Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局统一异常处理
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.info("================= 全局异常捕获 ===========================");
        log.error("全局异常捕获: " + req + " ex: " + e.toString());
        log.error("message: {}", e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("className: {}", stackTrace[0].getClassName());
        log.error("fileName: {}", stackTrace[0].getFileName());
        log.error("methodName: {}", stackTrace[0].getMethodName());
        log.error("errorLine: {}", stackTrace[0].getLineNumber());
        log.info("================= 全局异常捕获 ===========================");
        return R.error(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 自定义异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public R customerExceptionHandler(CustomerException e) {
        e.printStackTrace();
        log.info("================= 自定义异常捕获 ==========================");
        log.error("code：{}", e.getCode());
        log.error("message：{}", e.getMsg());
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("className: {}", stackTrace[0].getClassName());
        log.error("fileName: {}", stackTrace[0].getFileName());
        log.error("methodName: {}", stackTrace[0].getMethodName());
        log.error("errorLine: {}", stackTrace[0].getLineNumber());
        log.info("================= 自定义异常捕获 ==========================");
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
