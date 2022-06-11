package com.xiaoyuan.front.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.front.db.entity.ArticleLike;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ArticleLikeMapper
 * Author:      小袁
 * Date:        2022/5/11 9:28
 * Description:
 */
@Repository
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

    int removeLike(long articleId, long userId);

    int findCountByArticleId(long articleId);

    boolean isContainLike(long articleId, long userId);
}
