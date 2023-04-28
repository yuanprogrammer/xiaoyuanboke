package com.xiaoyuan.common.param.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteModParam {

    @NotNull
    private Integer id;
}
