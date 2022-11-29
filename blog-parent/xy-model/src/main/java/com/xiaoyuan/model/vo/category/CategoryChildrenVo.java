package com.xiaoyuan.model.vo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    CategoryChildrenVo
 * Author:      小袁
 * Date:        2022/4/15 14:18
 * Description: 子分类
 */
@Data
public class CategoryChildrenVo implements Serializable {

    private final static long serialVersionUID = 1L;

    // ID编号
    private Integer childrenId;

    // 分类栏目名称
    private String childrenName;
}
