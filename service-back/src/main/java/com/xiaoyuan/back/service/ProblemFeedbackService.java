package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.pojo.ProblemFeedback;
import com.xiaoyuan.common.param.problem.ProblemQueryParam;
import com.xiaoyuan.common.vo.ProblemFeedbackVo;
import com.xiaoyuan.common.vo.R;

/**
 * FileName:    ProblemFeedbackService
 * Author:      小袁
 * Date:        2022/4/21 8:47
 * Description:
 */
public interface ProblemFeedbackService extends IService<ProblemFeedback> {

    /**
     * 删除问题
     */
    R delete(Long id);

    /**
     * 编辑问题状态
     */
    R modifyProblemState(ProblemFeedbackVo problemFeedbackVo);

    /**
     * 分页查询
     */
    R listProblemFeedbackPage(ProblemQueryParam problemQueryParam);

    /**
     * 邮件通知用户问题解决
     */
    R noticeUserProblemIsOk(ProblemFeedbackVo problemFeedbackVo);
}
