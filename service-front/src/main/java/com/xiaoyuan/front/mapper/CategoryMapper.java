package com.xiaoyuan.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.front.vo.CategoryVo;
import com.xiaoyuan.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName:    CategoryMapper
 * Author:      小袁
 * Date:        2022/4/15 10:52
 * Description: 分类DAO
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有分类的目录结构
     * @return
     */
    List<CategoryVo> findCategoryList();

    /**
     * 查询文章的分类
     * @param articleId
     * @return
     */
    List<String> findArticleCategory(long articleId);
}
