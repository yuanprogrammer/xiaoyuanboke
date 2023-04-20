package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.entity.ArticleCollect;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.vo.R;

/**
 * FileName:    ArticleCollectService
 * Author:      小袁
 * Date:        2022/5/11 9:28
 * Description:
 */
public interface ArticleCollectService extends IService<ArticleCollect> {

    /**
     * 收藏
     */
    R collectArticle(ArticleLikeParam articleLikeParam);

    R getCollectInfo(String token, ArticleLikeParam articleLikeParam);
}
