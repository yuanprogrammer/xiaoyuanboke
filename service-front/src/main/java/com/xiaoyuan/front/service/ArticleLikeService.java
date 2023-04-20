package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.entity.ArticleLike;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.vo.R;

/**
 * FileName:    ArticleLikeService
 * Author:      小袁
 * Date:        2022/5/11 9:29
 * Description:
 */
public interface ArticleLikeService extends IService<ArticleLike> {

    /**
     * 点赞文章
     */
    R likeArticle(ArticleLikeParam articleLikeParam);

    /**
     * 获取文章点赞信息
     */
    R getLikeInfo(String token, ArticleLikeParam articleLikeParam);
}
