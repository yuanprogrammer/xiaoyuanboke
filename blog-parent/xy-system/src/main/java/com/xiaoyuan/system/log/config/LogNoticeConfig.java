package com.xiaoyuan.system.log.config;

import com.xiaoyuan.system.log.service.LogNotice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogNoticeConfig implements LogNotice {

    // 超时通知邮箱列表
    @Override
    public List<String> timeCostNotice() {
        return null;
    }

    // 业务错误通知邮箱列表
    @Override
    public List<String> serviceError() {
        return null;
    }

    // 系统异常通知邮箱列表
    @Override
    public List<String> SystemErrorNotice() {
        return null;
    }
}
