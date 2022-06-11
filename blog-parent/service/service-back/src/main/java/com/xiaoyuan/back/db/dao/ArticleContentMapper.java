package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.back.db.entity.ArticleContent;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ArticleContentMapper
 * Author:      小袁
 * Date:        2022/4/16 18:35
 * Description:
 */
@Repository
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {

    /**
     * 根据文章编号获取Markdown文本
     * @param id
     * @return
     */
    String getArticleMarkdownById(Long id);
}
