package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * FileName:    ArticleCategory
 * Author:      小袁
 * Date:        2022/4/16 18:07
 * Description: 文章表和分类表的中间表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "xy_article_category")
public class ArticleCategory {

    /**
     * 中间表的编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章编号
     */
    private Long articleId;

    /**
     * 文章分类编号
     */
    private Integer categoryId;
}
