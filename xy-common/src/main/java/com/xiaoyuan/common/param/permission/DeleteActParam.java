package com.xiaoyuan.common.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteActParam {

    @NotNull
    private Integer id;
}
