package com.xiaoyuan.tencent.service;

import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    SmsService
 * Author:      小袁
 * Date:        2022/5/9 13:32
 * Description:
 */
public interface SmsService {

    R sendSmsCode(String phone);
}
