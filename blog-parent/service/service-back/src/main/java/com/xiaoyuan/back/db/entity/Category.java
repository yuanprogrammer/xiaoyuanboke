package com.xiaoyuan.back.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * FileName:    Category
 * Author:      小袁
 * Date:        2022/4/15 10:42
 * Description: 分类实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 调用Setting方法后 回传对象
public class Category {

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
