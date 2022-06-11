package com.xiaoyuan.back.vo.param;

import lombok.Data;

/**
 * FileName:    UserQueryParam
 * Author:      小袁教程
 * Date:        2022/6/7 0:24
 * Description:
 */
@Data
public class UserQueryParam {

    private String id;

    private String username;

    private String nickname;

    private String email;

    private String mobileNumber;

    private String gmtCreate;
}
