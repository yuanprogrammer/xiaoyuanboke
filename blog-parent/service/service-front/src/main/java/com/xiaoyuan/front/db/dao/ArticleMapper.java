package com.xiaoyuan.front.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.front.db.entity.Article;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.front.vo.ArticleVo;
import com.xiaoyuan.front.vo.RecommendArticleVo;
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
     * @param limit
     * @return
     */
    List<RecommendArticleVo> findHosts(int limit);

    /**
     * 查询最新文章
     * @param limit
     * @return
     */
    List<RecommendArticleVo> findNews(int limit);

    /**
     * 查询文章档案
     * @return
     */
    List<ArchivesVo> findArchives();

    /**
     * 查询档案列表
     * @return
     */
    List<ArchivesVo> findArchivesArticleList();

    /**
     * 查询文章总数
     * @return
     */
    Integer findTotal();

    /**
     * 查询浏览总数
     * @return
     */
    Integer findViewCount();

    /**
     * 分页 - 条件 查询文章列表
     * @param page
     * @return
     */
    IPage<ArticleVo> findArticleList(Page<Article> page, String searchWord, String sortCondition);

    /**
     * 分页 - 条件 查询文章列表(方案二）
     * @param page
     * @param searchWord
     * @param sortCondition
     * @return
     */
    IPage<ArticleVo> findArticleList1(Page<Article> page, String searchWord, String sortCondition);

    /**
     * 根据分类编号查询文章列表
     * @param page 分页对象
     * @param categoryId 分类编号
     * @return
     */
    IPage<Article> findCategoryArticleList(Page<Article> page, Integer categoryId);

    /**
     * 根据文章编号查询详细文章信息
     * @param id
     * @return
     */
    ArticleVo getArticleDetailById(long id);

    /**
     * 文章浏览量 + 1
     * @param id
     * @return
     */
    int updateViewCount(long id);
}
