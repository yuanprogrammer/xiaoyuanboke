package com.xiaoyuan.back.db.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * FileName:    Article
 * Author:      小袁
 * Date:        2022/4/16 11:18
 * Description: 文章实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article {

    /**
     * 文章ID, 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private char deleted;

    /**
     * 访问量
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer viewCount;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtUpdate;

}
