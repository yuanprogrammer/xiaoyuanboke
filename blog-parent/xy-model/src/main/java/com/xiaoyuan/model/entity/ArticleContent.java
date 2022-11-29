package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * FileName:    ArticleContent
 * Author:      小袁
 * Date:        2022/4/16 11:40
 * Description: 文章内容表（markdown文本和html文本）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "xy_article_content")
public class ArticleContent implements Serializable {

    private static final long serialVersionUID = 1L;

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
