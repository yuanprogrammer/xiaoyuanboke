package com.xiaoyuan.model.constants;

/**
 * FileName:    MatchConstants
 * Author:      小袁教程
 * Date:        2022/9/3 11:23
 * Description: 正则表达式
 */

public interface MatchConstants {

    // 密码匹配
    String password = "";

    // 邮箱匹配
    String email = "^([a-zA-Z0-9]+[-_\\.]?)+@[a-zA-Z0-9]+\\.[a-z]+$";

    // 号码匹配
    String phone = "^[1][3,4,5,7,8][0-9]{9}$";
}
