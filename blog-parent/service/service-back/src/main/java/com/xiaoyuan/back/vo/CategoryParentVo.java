package com.xiaoyuan.back.vo;

import lombok.Data;

import java.util.List;

/**
 * FileName:    CategoryVo
 * Author:      小袁
 * Date:        2022/4/15 14:16
 * Description:
 */
@Data
public class CategoryParentVo {

    // 父级分类编号ID
    private Integer parentId;

    // 父级分类名称
    private String parentName;

    // 子分类
    private List<CategoryChildrenVo> childrenCategory;
}
