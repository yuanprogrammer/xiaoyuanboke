package com.xiaoyuan.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    UserOperationVo
 * Author:      小袁教程
 * Date:        2022/6/7 12:08
 * Description:
 */
@Data
public class UserOperationVo implements Serializable {

    private final static long serialVersionUID = 1L;

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
