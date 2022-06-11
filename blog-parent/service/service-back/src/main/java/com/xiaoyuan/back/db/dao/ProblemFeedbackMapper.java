package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.back.db.entity.ProblemFeedback;
import org.springframework.stereotype.Repository;

/**
 * FileName:    ProblemFeedbackMapper
 * Author:      小袁
 * Date:        2022/4/21 8:46
 * Description:
 */
@Repository
public interface ProblemFeedbackMapper extends BaseMapper<ProblemFeedback> {

    String getProblemFeedbackStateById(Long id);
}
