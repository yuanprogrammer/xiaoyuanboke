package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName:    UserOperation
 * Author:      小袁教程
 * Date:        2022/5/17 15:46
 * Description:
 */
@Data
@TableName(value = "xy_user_operation")
public class UserOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @TableField(value = "`describe`")
    private String describe;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
