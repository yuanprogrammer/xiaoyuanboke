package com.xiaoyuan.front.vo.param;

import lombok.Data;

/**
 * FileName:    ProblemFeedbackParam
 * Author:      小袁
 * Date:        2022/4/25 13:15
 * Description:
 */
@Data
public class ProblemFeedbackParam {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 问题内容
     */
    private String problem;
}
