package com.xiaoyuan.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleVo {

    private Integer id;

    private String roleKey;

    private String roleName;

    private Object permissions;

    private String describe;
}
