package com.xiaoyuan.common.param.sysuser;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserParam {

    @NotNull
    private String id;
}
