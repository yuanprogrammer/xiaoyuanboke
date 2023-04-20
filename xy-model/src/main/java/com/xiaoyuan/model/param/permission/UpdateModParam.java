package com.xiaoyuan.model.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateModParam {

    @NotNull
    private Integer id;

    @NotNull
    private String modKey;

    @NotNull
    private String modName;
}
