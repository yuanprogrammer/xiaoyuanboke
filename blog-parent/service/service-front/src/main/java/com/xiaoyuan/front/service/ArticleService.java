package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.Article;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.front.vo.ArticleVo;
import com.xiaoyuan.front.vo.RecommendArticleVo;
import com.xiaoyuan.front.vo.param.ArticleQueryParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

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
     * @return
     */
    List<RecommendArticleVo> findHostsArticle();

    /**
     * 查询最新文章
     * @return
     */
    List<RecommendArticleVo> findNewsArticle();

    /**
     * 查询文章档案
     * @return
     */
    List<ArchivesVo> findArchives();

    /**
     * 查询文章总数
     * @return
     */
    Integer findTotal();

    /**
     * 查询浏览总数
     * @return
     */
    String findViewCount();

    /**
     * 文章列表查询
     * @param articleQueryParam
     * @return
     */
    R listPageArticle(PageUtils pageUtils, ArticleQueryParam articleQueryParam);

    /**
     * 查询所有档案文章列表
     * @return
     */
    R listArchivesArticle();

    /**
     * 查询某个分类文章列表
     * @param id 分类编号
     * @return
     */
    R listCategoryArticleList(PageUtils pageUtils, Integer id);

    /**
     * 获取文章详细内容
     * @param number 编号
     * @return
     */
    R getArticleDetailById(String number);

    /**
     * 获取文章markdown文本内容
     * @param number
     * @return
     */
    R getArticleMarkdownById(String number);

    /**
     * 通过文章编号查询是否存在该文章
     * @param number
     * @return
     */
    boolean isExistById(String number);
}
