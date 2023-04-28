package com.xiaoyuan.common.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@TableName(value = "xy_suggest_feedback")
public class SuggestFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

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
