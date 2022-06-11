package com.xiaoyuan.front.vo.param;

import lombok.Data;

/**
 * FileName:    LoginParam
 * Author:      小袁
 * Date:        2022/5/1 22:53
 * Description:
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    private String passwordConfirm;

    /**
     * 1 -> 用户名登陆
     * 2 -> 邮箱登陆
     * 3 -> 手机号码登陆
     */
    private String select;

    // 验证码
    private String code;
}
