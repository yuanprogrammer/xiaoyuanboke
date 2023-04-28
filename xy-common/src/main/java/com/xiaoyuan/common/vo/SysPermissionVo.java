package com.xiaoyuan.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermissionVo {

    /**
     * 权限ID
     */
    private Integer id;

    /**
     * 操作编号
     */
    private String actKey;

    /**
     * 操作
     */
    private String actName;

    /**
     * 模块编号
     */
    private String modKey;

    /**
     * 模块
     */
    private String modName;
}
