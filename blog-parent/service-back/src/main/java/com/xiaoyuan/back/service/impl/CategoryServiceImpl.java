package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.ArticleCategoryMapper;
import com.xiaoyuan.back.mapper.CategoryMapper;
import com.xiaoyuan.back.service.CategoryService;
import com.xiaoyuan.model.entity.Category;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.CategoryParam;
import com.xiaoyuan.model.vo.category.CategoryParentVo;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * FileName:    CategoryServiceImpl
 * Author:      小袁
 * Date:        2022/4/15 10:54
 * Description: 分类栏目的实现类
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public R insert(CategoryParam categoryParam) {
        if (categoryParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        Category category = new Category();

        // 判断是一级分类还是二级分类
        if ("2".equals(categoryParam.getOneOrTwo())) {
            category.setParentId(categoryParam.getParentId());
        }else if ("1".equals(categoryParam.getOneOrTwo())) {
            category.setParentId(0);
        }else {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }
        // 设置名称, 插入数据
        category.setName(categoryParam.getName());
        return categoryMapper.insert(category) == 0 ? R.fail(HttpStatusEnum.ILLEGAL_OPERATION) : R.success();
    }

    @Override
    public R modify(Category category) {
        if (category == null || category.getId() == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        return categoryMapper.updateById(category) == 0 ? R.fail() : R.success();
    }

    @Override
    public R move(Category category) {
        if (category == null || category.getId() == null || category.getParentId() == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        Category category1 = new Category();
        category1.setId(category.getId());
        category1.setParentId(category.getParentId());
        return this.baseMapper.updateById(category1) == 0 ? R.fail(HttpStatusEnum.ILLEGAL_OPERATION) : R.success();
    }

    @Override
    public R remove(Integer id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.or();
        wrapper.eq("parent_id", id);
        return categoryMapper.delete(wrapper) == 0 ? R.fail() : R.success();
    }

    @Override
    public R getCategoryById(Integer id) {
        CategoryParentVo categoryParentVo = categoryMapper.getCategoryById(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("category", categoryParentVo);
        return categoryParentVo == null ? R.fail() : R.success(map);
    }

    @Override
    public R listCategory() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryMapper.findCategoryList());
        return R.success(map);
    }

    @Override
    public R findParentCategoryAll() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        wrapper.eq("parent_id", 0);

        HashMap<String, Object> map = new HashMap<>();
        map.put("parentList", this.baseMapper.selectList(wrapper));
        return R.success();
    }

    @Override
    public List<String> findCategoryNamesByArticleId(Long articleId) {
        // 先查询出文章对应的所有分类编号
        List<Integer> categoryIds = articleCategoryMapper.findCategoryIdsByArticleId(articleId);

        // 通过分类编号列表查询每个分类的名称
        if (categoryIds.size() == 0) return null;

        return categoryMapper.findCategoryNameByIds(categoryIds);
    }
}
