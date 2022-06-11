package com.xiaoyuan.back.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * FileName:    UserOperation
 * Author:      小袁教程
 * Date:        2022/5/17 15:46
 * Description:
 */
@Data
public class UserOperation {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @TableField(value = "`describe`")
    private String describe;

    private Date gmtCreate;
}
