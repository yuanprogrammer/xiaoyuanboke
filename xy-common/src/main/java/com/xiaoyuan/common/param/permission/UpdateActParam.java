package com.xiaoyuan.common.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateActParam {

    @NotNull
    private Integer id;

    @NotNull
    private String actKey;

    @NotNull
    private String actName;
}
