package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.ProblemFeedbackMapper;
import com.xiaoyuan.front.db.entity.ProblemFeedback;
import com.xiaoyuan.front.service.ProblemFeedbackService;
import com.xiaoyuan.front.vo.param.ProblemFeedbackParam;
import com.xiaoyuan.utils.StringUtil;
import com.xiaoyuan.utils.vo.R;
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
            return R.error().message("参数错误");
        }

        String email = problemFeedbackParam.getEmail();
        String problem = problemFeedbackParam.getProblem();
        // 非空校验
        if (StringUtils.isBlank(email) || StringUtils.isBlank(problem)) {
            return R.error().message("参数错误");
        }

        // check email -->> length is > 33 ? or legality ?
        if (email.length() > 33 || !StringUtil.checkEmail(email)) {
            return R.error().message("邮箱格式错误");
        }

        ProblemFeedback problemFeedback = new ProblemFeedback();
        problemFeedback.setEmail(problemFeedbackParam.getEmail());
        problemFeedback.setProblem(problemFeedbackParam.getProblem());

        return this.baseMapper.insert(problemFeedback) == 0 ? R.error().message("反馈失败") : R.ok();
    }
}
