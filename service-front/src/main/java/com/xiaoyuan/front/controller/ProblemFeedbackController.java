package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ProblemFeedbackService;
import com.xiaoyuan.front.service.SuggestFeedbackService;
import com.xiaoyuan.front.vo.param.ProblemFeedbackParam;
import com.xiaoyuan.common.pojo.SuggestFeedback;
import com.xiaoyuan.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    ProblemFeedbackController
 * Author:      小袁
 * Date:        2022/4/25 13:16
 * Description:
 */
@RestController
@RequestMapping("/feedback")
@Api(tags = "反馈管理")
public class ProblemFeedbackController {

    @Autowired
    private ProblemFeedbackService problemFeedbackService;

    @Autowired
    private SuggestFeedbackService suggestFeedbackService;

    @PostMapping("problem")
    @ApiOperation(value = "提交问题反馈")
    public R insertProblemFeedback(@RequestBody ProblemFeedbackParam problemFeedbackParam) {
        return problemFeedbackService.insert(problemFeedbackParam);
    }

    @PostMapping("suggest")
    @ApiOperation(value = "提交建议反馈")
    public R insertSuggestFeedback(@RequestBody SuggestFeedback suggestFeedback) {
        return suggestFeedbackService.insert(suggestFeedback);
    }
}
