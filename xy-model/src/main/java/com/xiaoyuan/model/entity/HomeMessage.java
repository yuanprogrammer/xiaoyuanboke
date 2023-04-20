package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName:    HomeMessage
 * Author:      小袁
 * Date:        2022/4/27 19:21
 * Description: 主页留言 表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "xy_home_message")
public class HomeMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 留言编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 留言人编号
     */
    private Long authorId;

    /**
     * 简述留言（正面）
     */
    private String content;

    /**
     * 详细留言（背面, 可选）
     */
    private String detailContent;

    /**
     * 背景图（随机）
     */
    @TableField(fill = FieldFill.INSERT)
    private String background;

    /**
     * 留言时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
