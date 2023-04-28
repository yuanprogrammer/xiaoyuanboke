package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.common.pojo.Article;
import com.xiaoyuan.common.param.article.ArticleQueryParam;
import com.xiaoyuan.common.param.article.CategoryQueryParam;
import com.xiaoyuan.common.vo.R;
import com.xiaoyuan.common.vo.article.RecommendArticleVo;

import java.util.List;

/**
 * FileName:    ArticleService
 * Author:      小袁
 * Date:        2022/4/24 17:44
 * Description:
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查询热门文章
     */
    List<RecommendArticleVo> findHostsArticle();

    /**
     * 查询最新文章
     */
    List<RecommendArticleVo> findNewsArticle();

    /**
     * 查询文章档案
     */
    List<ArchivesVo> findArchives();

    /**
     * 查询文章总数
     */
    Integer findTotal();

    /**
     * 查询浏览总数
     */
    String findViewCount();

    /**
     * 文章列表查询
     */
    R listPageArticle(ArticleQueryParam articleQueryParam);

    /**
     * 查询所有档案文章列表
     */
    R listArchivesArticle();

    /**
     * 查询某个分类文章列表
     */
    R listCategoryArticleList(CategoryQueryParam categoryQueryParam);

    /**
     * 获取文章详细内容
     */
    R getArticleDetailById(String number);

    /**
     * 获取文章markdown文本内容
     */
    R getArticleMarkdownById(String number);

    /**
     * 通过文章编号查询是否存在该文章
     */
    boolean isExistById(String number);
}
