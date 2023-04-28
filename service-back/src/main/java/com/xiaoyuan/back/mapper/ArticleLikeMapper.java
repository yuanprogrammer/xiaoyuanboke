package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.common.pojo.ArticleLike;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ArticleLikeMapper
 * Author:      小袁
 * Date:        2022/5/11 9:28
 * Description:
 */
@Repository
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

    int removeByArticleId(long articleId);
}
