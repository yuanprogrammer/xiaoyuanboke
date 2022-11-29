package com.xiaoyuan.front.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    CommonUserVo
 * Author:      小袁
 * Date:        2022/5/2 19:50
 * Description:
 */
@Data
public class CommonUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户编号
    private String number;

    private String comUserCode;

    // 用户名
    private String username;

    // 昵称
    private String nickname;

    // 邮箱
    private String email;

    // 手机号码
    private String mobileNumber;

    // 头像
    private String avatar;
}
