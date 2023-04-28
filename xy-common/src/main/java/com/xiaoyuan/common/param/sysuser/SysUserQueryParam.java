package com.xiaoyuan.common.param.sysuser;

import com.xiaoyuan.common.vo.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryParam extends Pagination implements Serializable {

    private static final long serialVersionUID = 1L;
}
