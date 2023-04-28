package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.common.pojo.ProblemFeedback;
import com.xiaoyuan.common.param.problem.ProblemQueryParam;
import com.xiaoyuan.common.vo.ProblemFeedbackVo;
import org.apache.ibatis.annotations.Param;
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

    IPage<ProblemFeedbackVo> listProblemFeedbackPage(Page<ProblemFeedbackVo> page, @Param("param") ProblemQueryParam problemQueryParam);
}
