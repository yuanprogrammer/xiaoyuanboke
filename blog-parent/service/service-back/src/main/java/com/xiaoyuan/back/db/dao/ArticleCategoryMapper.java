package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.back.db.entity.ArticleCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName:    ArticleCategoryMapper
 * Author:      小袁
 * Date:        2022/4/16 18:10
 * Description:
 */
@Repository
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 根据文章编号查询他的所属分类
     * @param articleId
     * @return
     */
    List<Integer> findCategoryIdsByArticleId(Long articleId);
}
