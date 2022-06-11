package com.xiaoyuan.back.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * FileName:    SuggestFeedback
 * Author:      小袁
 * Date:        2022/4/28 19:08
 * Description: 建议反馈 表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SuggestFeedback {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 建议人
     */
    private String name;

    /**
     * 建议内容
     */
    private String content;

    /**
     * 反馈时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
