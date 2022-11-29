package com.xiaoyuan.model.param.sysuser;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserParam {

    @NotNull
    private String id;
}
