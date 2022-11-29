package com.xiaoyuan.model.param.role;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InsertRoleParam {

    @NotNull
    @Length(max = 32)
    private String roleKey;

    @NotNull
    @Length(max = 32)
    private String roleName;

    @NotNull
    @Length(max = 64)
    private String describe;

    @NotNull
    private List<Integer> permissions;
}
