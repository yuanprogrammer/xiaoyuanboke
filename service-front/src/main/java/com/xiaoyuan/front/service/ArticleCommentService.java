package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.model.entity.ArticleComment;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.param.article.CommentDeleteParam;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;

/**
 * FileName:    ArticleCommentService
 * Author:      小袁
 * Date:        2022/5/3 19:30
 * Description:
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 插入一条评论
     */
    R insert(ArticleCommentParam articleCommentParam);

    /**
     * 删除评论
     */
    R delete(String token, CommentDeleteParam commentDeleteParam);

    /**
     * 修改评论内容
     */
    R modify(String token, ArticleCommentParam articleCommentParam);

    /**
     * 查询文章评论
     */
    R listCommentPage(String token, ArticleLikeParam articleLikeParam);
}
