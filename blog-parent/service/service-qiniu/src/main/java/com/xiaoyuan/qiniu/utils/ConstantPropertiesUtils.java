package com.xiaoyuan.qiniu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * FileName:    ConstantPropertiesUtils
 * Author:      小袁
 * Date:        2022/4/3 13:15
 * Description:
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.accessSecretKey}")
    private String accessSecretKey;

    @Value("${qiniu.articleImgBucket}")
    private String articleImgBucket;

    public static String ACCESS_KEY;
    public static String ACCESS_SECRET_KEY;
    public static String ARTICLE_IMG_BUCKET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY = this.accessKey;
        ACCESS_SECRET_KEY = this.accessSecretKey;
        ARTICLE_IMG_BUCKET = this.articleImgBucket;
    }
}
