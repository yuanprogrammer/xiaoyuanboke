package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.param.ProblemFeedbackParam;
import com.xiaoyuan.common.pojo.ProblemFeedback;
import com.xiaoyuan.common.vo.R;

/**
 * FileName:    ProblemFeedbackService
 * Author:      小袁
 * Date:        2022/4/25 13:18
 * Description:
 */
public interface ProblemFeedbackService extends IService<ProblemFeedback> {

    /**
     * 插入一条问题反馈数据
     * @param problemFeedbackParam
     * @return
     */
    R insert(ProblemFeedbackParam problemFeedbackParam);
}
