package com.xiaoyuan.system.log.service;

import java.util.List;

public interface LogNotice {

    /**
     * 超时日志通知列表
     *
     * @return 要通知的邮箱列表
     */
    List<String> timeCostNotice();

    /**
     * 业务错误日志通知列表
     *
     * @return 要通知的邮箱列表
     */
    List<String> serviceError();

    /**
     * 系统异常日志通知列表
     *
     * @return 要通知的邮箱列表
     */
    List<String> SystemErrorNotice();
}
