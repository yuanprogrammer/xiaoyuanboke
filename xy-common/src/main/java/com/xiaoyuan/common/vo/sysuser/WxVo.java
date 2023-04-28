package com.xiaoyuan.common.vo.sysuser;

import lombok.Data;

@Data
public class WxVo {

    private String access_token;

    private String expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

    private String unionid;

    private String errcode;

    private String invalid;
}
