package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.model.entity.ArticleComment;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ArticleCommentMapper
 * Author:      小袁
 * Date:        2022/5/3 19:30
 * Description:
 */
@Repository
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    int removeByArticleId(long articleId);
}
