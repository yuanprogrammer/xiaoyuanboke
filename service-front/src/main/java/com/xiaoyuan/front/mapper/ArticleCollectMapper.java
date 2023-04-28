package com.xiaoyuan.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.common.pojo.ArticleCollect;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ArticleCollectMapper
 * Author:      小袁
 * Date:        2022/5/11 9:27
 * Description:
 */
@Repository
public interface ArticleCollectMapper extends BaseMapper<ArticleCollect> {

    int removeCollect(long articleId, long userId);

    int findCountByArticleId(long articleId);

    boolean isContainCollect(long articleId, long userId);
}
