package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.back.db.entity.Article;
import com.xiaoyuan.back.vo.ArticleVo;
import org.springframework.stereotype.Repository;


/**
 * FileName:    ArticleMapper
 * Author:      小袁
 * Date:        2022/4/16 11:23
 * Description: 文章DAO
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleVo> selectArticleList(Page<ArticleVo> page);
}
