package com.xiaoyuan.model.vo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    CategoryNameVo
 * Author:      小袁
 * Date:        2022/4/17 13:14
 * Description: 只有分类名的对象
 */
@Data
public class CategoryNameVo implements Serializable {

    private final static long serialVersionUID = 1L;

    // 分类名称
    private String name;
}
