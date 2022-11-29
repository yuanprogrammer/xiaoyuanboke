package com.xiaoyuan.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * FileName:    Category
 * Author:      小袁
 * Date:        2022/4/15 10:42
 * Description: 分类实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 调用Setting方法后 回传对象
@TableName(value = "xy_category")
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类栏目名称
     */
    private String name;

    /**
     * 父级栏目
     */
    private Integer parentId;
}
