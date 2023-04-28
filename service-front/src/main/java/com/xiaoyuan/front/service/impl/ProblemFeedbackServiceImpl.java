package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common.util.StringMatch;
import com.xiaoyuan.front.mapper.ProblemFeedbackMapper;
import com.xiaoyuan.front.service.ProblemFeedbackService;
import com.xiaoyuan.front.vo.param.ProblemFeedbackParam;
import com.xiaoyuan.common.pojo.ProblemFeedback;
import com.xiaoyuan.common.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:    ProblemFeedbackServiceImpl
 * Author:      小袁
 * Date:        2022/4/25 13:18
 * Description:
 */
@Service
@Transactional
public class ProblemFeedbackServiceImpl extends ServiceImpl<ProblemFeedbackMapper, ProblemFeedback> implements ProblemFeedbackService {

    @Override
    public R insert(ProblemFeedbackParam problemFeedbackParam) {
        // 非空校验
        if (problemFeedbackParam == null) {
            return R.fail().message("参数错误");
        }

        String email = problemFeedbackParam.getEmail();
        String problem = problemFeedbackParam.getProblem();
        // 非空校验
        if (StringUtils.isBlank(email) || StringUtils.isBlank(problem)) {
            return R.fail().message("参数错误");
        }

        if (email.length() > 33 || !StringMatch.isEmail(email)) {
            return R.fail().message("邮箱格式错误");
        }

        ProblemFeedback problemFeedback = new ProblemFeedback();
        problemFeedback.setEmail(problemFeedbackParam.getEmail());
        problemFeedback.setProblem(problemFeedbackParam.getProblem());

        return this.baseMapper.insert(problemFeedback) == 0 ? R.fail().message("反馈失败") : R.success();
    }
}
