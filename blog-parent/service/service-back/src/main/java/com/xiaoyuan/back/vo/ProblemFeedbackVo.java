package com.xiaoyuan.back.vo;

import lombok.Data;

/**
 * FileName:    ProblemFeedbackVo
 * Author:      小袁
 * Date:        2022/4/21 8:49
 * Description: 问题反馈 param
 */
@Data
public class ProblemFeedbackVo {

    /**
     * 编号
     */
    private String id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 问题描述
     */
    private String problem;

    /**
     * 问题状态
     */
    private Character problemState;

    /**
     * 通知状态
     */
    private Character noticeState;

    /**
     * 反馈时间
     */
    private String createTime;
}
