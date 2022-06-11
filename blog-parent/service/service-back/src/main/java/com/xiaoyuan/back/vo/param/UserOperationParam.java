package com.xiaoyuan.back.vo.param;

import lombok.Data;

/**
 * FileName:    UsesrOperationParam
 * Author:      小袁教程
 * Date:        2022/6/7 12:14
 * Description:
 */
@Data
public class UserOperationParam {

    private String nickname;
    private String username;
    private String email;
    private String mobileNumber;
    private String describe;
    private String operatedTime;
}
