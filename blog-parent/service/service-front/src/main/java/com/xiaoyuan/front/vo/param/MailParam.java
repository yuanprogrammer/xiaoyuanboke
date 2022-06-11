package com.xiaoyuan.front.vo.param;

import lombok.Data;

/**
 * FileName:    MailParam
 * Author:      小袁
 * Date:        2022/5/2 19:06
 * Description:
 */
@Data
public class MailParam {

    // 收件人
    private String mail;

    // 验证码
    private String code;
}
