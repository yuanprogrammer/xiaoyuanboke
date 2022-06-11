package com.xiaoyuan.back.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * FileName:    ArticleContent
 * Author:      小袁
 * Date:        2022/4/16 11:40
 * Description: 文章内容表（markdown文本和html文本）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleContent {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章编号
     */
    private Long articleId;

    /**
     * markdown文本
     */
    private String content;

    /**
     * html格式文本
     */
    private String contentHtml;
}
