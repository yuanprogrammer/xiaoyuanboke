package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.entity.Category;
import com.xiaoyuan.model.param.CategoryParam;
import com.xiaoyuan.model.vo.R;

import java.util.List;

/**
 * FileName:    CategoryService
 * Author:      小袁
 * Date:        2022/4/15 10:52
 * Description: 分类栏目 Service
 */
public interface CategoryService extends IService<Category> {

    /**
     * 新增分类栏目数据
     * @param categoryParam
     * @return
     */
    R insert(CategoryParam categoryParam);

    /**
     * 根据分类栏目的ID进行修改数据
     * @param category
     * @return
     */
    R modify(Category category);

    /**
     * 移动分类位置
     * @param category
     * @return
     */
    R move(Category category);

    /**
     * 根据分类栏目的ID进行删除
     * @param id
     * @return
     */
    R remove(Integer id);

    /**
     * 通过某个父级分类的ID查询该父级的所有子分类
     * @param id
     * @return
     */
    R getCategoryById(Integer id);

    /**
     * 查询所有分类的目录结构
     * @return
     */
    R listCategory();

    /**
     * 查询所有父级编号
     * @return
     */
    R findParentCategoryAll();

    /**
     * 通过文章编号查询文章的分类信息
     * @param articleId 文章编号
     * @return
     */
    List<String> findCategoryNamesByArticleId(Long articleId);
}
