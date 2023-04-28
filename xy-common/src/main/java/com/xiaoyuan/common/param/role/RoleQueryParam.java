package com.xiaoyuan.common.param.role;

import com.xiaoyuan.common.vo.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQueryParam extends Pagination {

    private String roleName;
}
