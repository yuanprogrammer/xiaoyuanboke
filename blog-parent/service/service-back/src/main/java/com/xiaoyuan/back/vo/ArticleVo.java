package com.xiaoyuan.back.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * FileName:    ArticleVo
 * Author:      小袁
 * Date:        2022/4/17 13:43
 * Description:
 */
@Data
public class ArticleVo {

    /**
     * 文章编号
     */
    private String id;

    /**
     * 作者编号
     */
    private String authorId;

    /**
     * 文章标题
     */
    private String title;

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
    private List<String> category;

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
     * 文章发布时间
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date gmtCreate;
}
