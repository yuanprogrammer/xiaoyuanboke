package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.common.pojo.Category;
import com.xiaoyuan.common.vo.category.CategoryParentVo;
import com.xiaoyuan.common.vo.category.CategoryVo;
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
     * 通过某个父级分类的ID查询该父级的所有子分类
     * @param id
     * @return
     */
    CategoryParentVo getCategoryById(Integer id);

    /**
     * 根据分类编号列表查询分类名称
     * @param categoryIds 分类编号列表
     * @return
     */
    List<String> findCategoryNameByIds(List<Integer> categoryIds);

    /**
     * 通过分类编号查询完整的分类信息
     * @param categoryIds 编号列表
     * @return 拼接的编号
     */
    List<String> getCompleteCategoryByIds(List<Integer> categoryIds);
}
