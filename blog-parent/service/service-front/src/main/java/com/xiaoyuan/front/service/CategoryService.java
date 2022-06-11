package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.Category;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    CategoryService
 * Author:      小袁
 * Date:        2022/5/5 9:19
 * Description:
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询所有分类的目录结构
     * @return
     */
    R listCategory();
}
