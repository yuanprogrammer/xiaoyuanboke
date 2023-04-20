package com.xiaoyuan.model.param.sysuser;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeChatRegisterParam {

    @NotNull
    private String openId;
}
