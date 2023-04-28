package com.xiaoyuan.common.vo.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * FileName:    ArticleVo
 * Author:      小袁
 * Date:        2022/4/17 13:43
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleVo implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 文章编号
     */
    private String id;

    // 编号
    private String number;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章描述
     */
    private String digest;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 文章标题
     */
    private String tags;

    /**
     * 文章分类
     */
    private List<String> categoryList;

    /**
     * 点赞量
     */
    private Integer goodCount;

    /**
     * 收藏数量
     */
    private Integer collectCount;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 文章评论数量
     */
    private Integer commentCount;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 编辑时间
     */
    private String editTime;
}
