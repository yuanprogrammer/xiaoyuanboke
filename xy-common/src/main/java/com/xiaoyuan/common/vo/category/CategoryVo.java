package com.xiaoyuan.common.vo.category;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * FileName:    CategoryVo
 * Author:      小袁
 * Date:        2022/5/10 19:12
 * Description:
 */
@Data
public class CategoryVo implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 分类编号
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 文章总数
     */
    private int count;

    /**
     * 子分类
     */
    private List<CategoryVo> children;
}
