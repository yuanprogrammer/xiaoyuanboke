package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_permission")
public class SysPermission {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer moduleId;

    private Integer actionId;
}
