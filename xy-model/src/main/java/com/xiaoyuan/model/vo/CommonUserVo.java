package com.xiaoyuan.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName:    CommonUserVo
 * Author:      小袁
 * Date:        2022/4/18 16:30
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonUserVo implements Serializable {

    private final static long serialVersionUID = 1L;

    private String id;

    private String username;

    private String nickname;

    private String email;

    private String mobileNumber;

    private String wechatNumber;

    private String avatar;

    private Date gmtCreate;

    private Date gmtLogin;
}
