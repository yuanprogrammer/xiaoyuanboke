package com.xiaoyuan.front.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * FileName:    ArticleLike
 * Author:      小袁
 * Date:        2022/5/11 9:25
 * Description: 文章点赞中间表
 */
@Data
public class ArticleLike {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long articleId;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
