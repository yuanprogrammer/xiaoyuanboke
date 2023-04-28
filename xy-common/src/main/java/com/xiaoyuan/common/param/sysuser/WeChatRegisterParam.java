package com.xiaoyuan.common.param.sysuser;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeChatRegisterParam {

    @NotNull
    private String openId;
}
