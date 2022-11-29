package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.mapper.SuggestFeedbackMapper;
import com.xiaoyuan.front.service.SuggestFeedbackService;
import com.xiaoyuan.model.entity.SuggestFeedback;
import com.xiaoyuan.model.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:    SuggestFeedbackServiceImpl
 * Author:      小袁
 * Date:        2022/4/28 19:11
 * Description:
 */
@Service
@Transactional
public class SuggestFeedbackServiceImpl extends ServiceImpl<SuggestFeedbackMapper, SuggestFeedback> implements SuggestFeedbackService {

    @Override
    public R insert(SuggestFeedback suggestFeedback) {
        if (suggestFeedback == null) {
            return R.fail().message("参数错误");
        }
        if (StringUtils.isBlank(suggestFeedback.getContent())) {
            return R.fail().message("参数错误");
        }
        if (!StringUtils.isBlank(suggestFeedback.getName()) && suggestFeedback.getName().length() > 20) {
            return R.fail().message("参数错误");
        }
        if (suggestFeedback.getContent().length() > 255) {
            return R.fail().message("参数错误");
        }

        return this.baseMapper.insert(suggestFeedback) == 0 ? R.fail().message("提交失败") : R.success();
    }
}
