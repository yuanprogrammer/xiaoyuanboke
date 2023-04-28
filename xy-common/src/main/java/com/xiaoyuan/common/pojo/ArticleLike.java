package com.xiaoyuan.common.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName:    ArticleLike
 * Author:      小袁
 * Date:        2022/5/11 9:25
 * Description: 文章点赞中间表
 */
@Data
@TableName(value = "xy_article_like")
public class ArticleLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long articleId;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
