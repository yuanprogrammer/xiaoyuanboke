package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.CategoryMapper;
import com.xiaoyuan.front.db.entity.Category;
import com.xiaoyuan.front.service.CategoryService;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * FileName:    CategoryServiceImpl
 * Author:      小袁
 * Date:        2022/5/5 9:19
 * Description:
 */
@Repository
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R listCategory() {
        return R.ok().data("categoryList", categoryMapper.findCategoryList());
    }
}
