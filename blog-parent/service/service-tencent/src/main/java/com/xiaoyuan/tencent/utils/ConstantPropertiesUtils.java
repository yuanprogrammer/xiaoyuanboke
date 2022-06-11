package com.xiaoyuan.tencent.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * FileName:    ConstantPropertiesUtils
 * Author:      小袁
 * Date:        2022/4/5 13:13
 * Description:
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencent.secretKey}")
    private String secretKey;

    public static String SECRET_ID;
    public static String SECRET_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = this.secretId;
        SECRET_KEY = this.secretKey;
    }
}
