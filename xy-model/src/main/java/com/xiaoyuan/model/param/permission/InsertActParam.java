package com.xiaoyuan.model.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InsertActParam {

    @NotNull
    private String actKey;

    @NotNull
    private String actName;
}
