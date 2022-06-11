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
 * FileName:    ProblemFeedback
 * Author:      小袁
 * Date:        2022/4/21 8:41
 * Description: 问题反馈表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProblemFeedback {

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 问题描述
     */
    private String problem;

    /**
     * 问题状态（0-未解决，1-处理中，2-已解决）
     */
    @TableField(fill = FieldFill.INSERT)
    private Character problemState;

    /**
     * 通知状态（0-未通知，1-已通知）
     */
    @TableField(fill = FieldFill.INSERT)
    private Character noticeState;

    /**
     * 反馈时间
     */
    private Date gmtCreate;

    /**
     * 处理时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date gmtUpdate;
}
