package com.xiaoyuan.front.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * FileName:    ArticleVo
 * Author:      小袁
 * Date:        2022/4/26 9:21
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleVo {

    // 编号
    private String number;

    // 标题
    private String title;

    // 摘要
    private String digest;

    // 封面
    private String cover;

    // 标签
    private String tags;

    // 分类
    private List<String> categoryList;

    // 浏览量
    private Integer viewCount;

    // 评论数量
    private Integer commentCount;

    // 点赞数量
    private Integer likeCount;

    // 收藏数量
    private Integer collectCount;

    // 发布时间
    private String publishTime;

    // 编辑时间
    private String editTime;

    // 用户昵称
    private String authorName;

    // 用户头像
    private String authorAvatar;
}
