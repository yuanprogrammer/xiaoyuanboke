package com.xiaoyuan.model.param.role;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PermissionRoleParam {

    @NotNull
    private String roleKey;

    @NotNull
    private List<Integer> permissions;
}
