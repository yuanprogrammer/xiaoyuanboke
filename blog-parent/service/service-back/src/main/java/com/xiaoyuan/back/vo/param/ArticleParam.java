package com.xiaoyuan.back.vo.param;

import lombok.Data;

import java.util.List;

/**
 * FileName:    ArticleParam
 * Author:      小袁
 * Date:        2022/4/16 11:37
 * Description:
 */
@Data
public class ArticleParam {

    /**
     * 文章编号
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 文章 markdown文本和html格式文本
     */
    private ArticleContentParam articleContentParam;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 分类专栏
     */
    private List<Integer> categoryList;
}
