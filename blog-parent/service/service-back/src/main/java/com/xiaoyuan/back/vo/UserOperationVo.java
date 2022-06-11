package com.xiaoyuan.back.vo;

import lombok.Data;

/**
 * FileName:    UserOperationVo
 * Author:      小袁教程
 * Date:        2022/6/7 12:08
 * Description:
 */
@Data
public class UserOperationVo {

    private Integer id;

    private String nickname;

    private String username;

    private String email;

    private String mobileNumber;

    private String wechatNumber;

    private String avatar;

    private String describe;

    private String operatedTime;
}
