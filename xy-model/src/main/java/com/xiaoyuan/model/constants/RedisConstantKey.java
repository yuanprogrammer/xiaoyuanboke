package com.xiaoyuan.model.constants;

/**
 * FileName:    TokenConstant
 * Author:      小袁
 * Date:        2022/5/9 10:01
 * Description:
 */
public interface RedisConstantKey {

    String USER = "USER_TOKEN_"; // 用户key前缀
    int USER_EXPIRE = 24 * 60 * 60; // 缓存时间（1天）

    String EMAIL = "EMAIL_"; // 邮箱前缀
    int EMAIL_EXPIRE = 5 * 60; // 缓存时间（五分钟）

    String MOBILE_NUMBER = "MOBILE_NUMBER_"; // 号码前缀
    int MOBILE_NUMBER_EXPIRE = 10 * 60; // 10分钟有效期

    String MOBILE_NUMBER_REQUEST_VERIFY = "MOBILE_NUMBER_REQUEST_VERIFY_"; // 发送短信前的权限码

    String EMAIL_REQUEST_VERIFY = "EMAIL_REQUEST_VERIFY_"; // 发送邮箱前的权限码

    int EXPIRE_TEN_SECOND = 10; // 10s
    int EXPIRE_ONE_MINUTE = 60; // 1分钟
}
