package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.back.db.entity.ProblemFeedback;
import com.xiaoyuan.back.vo.ProblemFeedbackVo;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    ProblemFeedbackService
 * Author:      小袁
 * Date:        2022/4/21 8:47
 * Description:
 */
public interface ProblemFeedbackService extends IService<ProblemFeedback> {

    /**
     * 删除问题
     * @param id
     * @return
     */
    R delete(Long id);

    /**
     * 编辑问题状态
     * @param problemFeedbackVo
     * @return
     */
    R modifyProblemState(ProblemFeedbackVo problemFeedbackVo);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    R listProblemFeedbackPage(Integer pageIndex, Integer pageSize);

    /**
     * 邮件通知用户问题解决
     * @param problemFeedbackVo
     * @return
     */
    R noticeUserProblemIsOk(ProblemFeedbackVo problemFeedbackVo);
}
