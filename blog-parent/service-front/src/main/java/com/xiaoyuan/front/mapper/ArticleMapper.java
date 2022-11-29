package com.xiaoyuan.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.model.entity.Article;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.vo.article.ArticleVo;
import com.xiaoyuan.model.vo.article.RecommendArticleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName:    ArticleMapper
 * Author:      小袁
 * Date:        2022/4/16 11:23
 * Description: 文章DAO
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询热门文章
     */
    List<RecommendArticleVo> findHosts(int limit);

    /**
     * 查询最新文章
     */
    List<RecommendArticleVo> findNews(int limit);

    /**
     * 查询文章档案
     */
    List<ArchivesVo> findArchives();

    /**
     * 查询档案列表
     */
    List<ArchivesVo> findArchivesArticleList();

    /**
     * 查询文章总数
     */
    Integer findTotal();

    /**
     * 查询浏览总数
     */
    Integer findViewCount();

    /**
     * 分页 - 条件 查询文章列表
     */
    IPage<ArticleVo> findArticleList(Page<ArticleVo> page, @Param("param") ArticleQueryParam articleQueryParam);

    /**
     * 分页 - 条件 查询文章列表(方案二）
     */
    IPage<ArticleVo> findArticleList1(Page<Article> page, String searchWord, String sortCondition);

    /**
     * 根据分类编号查询文章列表
     */
    IPage<Article> findCategoryArticleList(Page<Article> page, Integer categoryId);

    /**
     * 根据文章编号查询详细文章信息
     */
    ArticleVo getArticleDetailById(long id);

    /**
     * 文章浏览量 + 1
     */
    int updateViewCount(long id);
}
