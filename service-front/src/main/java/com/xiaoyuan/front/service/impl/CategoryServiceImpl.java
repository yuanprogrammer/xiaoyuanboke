package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.mapper.CategoryMapper;
import com.xiaoyuan.front.service.CategoryService;
import com.xiaoyuan.common.pojo.Category;
import com.xiaoyuan.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryMapper.findCategoryList());

        return R.success(map);
    }
}
