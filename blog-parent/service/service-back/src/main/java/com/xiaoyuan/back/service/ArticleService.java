package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.back.db.entity.Article;
import com.xiaoyuan.back.vo.param.ArticleParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    ArticleService
 * Author:      小袁
 * Date:        2022/4/16 11:23
 * Description: 文章业务接口
 */
public interface ArticleService extends IService<Article> {

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    R insert(ArticleParam articleParam);

    /**
     * 文章分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    R listArticlePage(Integer pageIndex, Integer pageSize);

    /**
     * 根据文章编号删除文章
     * @param id 编号
     * @return
     */
    R removeArticleById(Long id);

    /**
     * 根据文章编号查询文章详细内容（HTML格式）
     * @param articleId
     * @return
     */
    R getArticleDetailById(Long articleId);

    /**
     * 通过文章编号获取文章发布状态信息
     * @param articleId
     * @return
     */
    R getArticlePublishById(Long articleId);

    /**
     * 修改文章
     * @param articleParam 文章信息
     * @return 修改结果
     */
    R modifyArticleById(ArticleParam articleParam);
}
