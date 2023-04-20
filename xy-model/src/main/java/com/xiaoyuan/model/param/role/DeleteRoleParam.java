package com.xiaoyuan.model.param.role;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteRoleParam {

    @NotNull
    private Integer id;
}
