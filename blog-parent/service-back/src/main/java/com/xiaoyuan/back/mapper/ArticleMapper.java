package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.model.entity.Article;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.vo.article.ArticleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * FileName:    ArticleMapper
 * Author:      小袁
 * Date:        2022/4/16 11:23
 * Description: 文章DAO
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleVo> listArticlePage(Page<ArticleVo> page, @Param("param") ArticleQueryParam articleQueryParam);
}
