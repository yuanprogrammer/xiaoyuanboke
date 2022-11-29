package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.Article;
import com.xiaoyuan.model.param.ArticleParam;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.vo.article.ArticleVo;
import com.xiaoyuan.model.vo.R;

import java.util.List;

/**
 * FileName:    ArticleService
 * Author:      小袁
 * Date:        2022/4/16 11:23
 * Description: 文章业务接口
 */
public interface ArticleService extends IService<Article> {

    /**
     * 发布文章
     */
    R<String> insert(ArticleParam articleParam);

    /**
     * 文章分页查询
     */
    R<PageVo<List<ArticleVo>>> listArticlePage(ArticleQueryParam articleQueryParam);

    /**
     * 根据文章编号删除文章
     */
    R removeArticleById(Long id);

    /**
     * 根据文章编号查询文章详细内容（HTML格式）
     */
    R getArticleDetailById(Long articleId);

    /**
     * 通过文章编号获取文章发布状态信息
     */
    R getArticlePublishById(Long articleId);

    /**
     * 修改文章
     * @param articleParam 文章信息
     * @return 修改结果
     */
    R modifyArticleById(ArticleParam articleParam);
}
