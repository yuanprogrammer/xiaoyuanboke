package com.xiaoyuan.model.param.sysuser;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SysUserLoginParam {

    @NotNull
//    @Max(value = 32)
    private String username;

    @NotNull
//    @Max(value = 128)
    private String password;

    private String openId;
}
