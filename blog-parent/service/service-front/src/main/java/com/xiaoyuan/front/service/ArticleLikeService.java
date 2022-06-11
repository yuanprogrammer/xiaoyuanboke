package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.ArticleLike;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    ArticleLikeService
 * Author:      小袁
 * Date:        2022/5/11 9:29
 * Description:
 */
public interface ArticleLikeService extends IService<ArticleLike> {

    /**
     * 点赞文章
     * @param number
     * @return
     */
    R likeArticle(String number);

    /**
     * 获取文章点赞信息
     * @param token
     * @return
     */
    R getLikeInfo(String token, String number);
}
