package com.xiaoyuan.model.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InsertModParam {

    @NotNull
    private String modKey;

    @NotNull
    private String modName;
}
