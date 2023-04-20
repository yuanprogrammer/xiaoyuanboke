package com.xiaoyuan.model.param.role;

import com.xiaoyuan.model.common.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQueryParam extends Pagination {

    private String roleName;
}
