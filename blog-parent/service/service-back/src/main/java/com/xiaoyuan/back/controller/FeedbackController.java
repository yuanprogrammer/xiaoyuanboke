package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.ProblemFeedbackService;
import com.xiaoyuan.back.service.SuggestFeedbackService;
import com.xiaoyuan.back.vo.ProblemFeedbackVo;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    FeedbackController
 * Author:      小袁
 * Date:        2022/4/30 15:35
 * Description: 反馈管理
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private ProblemFeedbackService problemFeedbackService;

    /**
     * 问题反馈
     */

    @DeleteMapping("problem/{id}")
    @ApiOperation(value = "删除问题")
    public R deleteProblem(@PathVariable(value = "id") String id) {
        return problemFeedbackService.delete(Long.parseLong(id));
    }

    @PutMapping("problem")
    @ApiOperation(value = "编辑问题状态")
    public R modifyProblemState(@RequestBody ProblemFeedbackVo problemFeedbackVo) {
        return problemFeedbackService.modifyProblemState(problemFeedbackVo);
    }

    @GetMapping("problem")
    public R findProblemFeedbackList() {
        return problemFeedbackService.listProblemFeedbackPage(1, 10);
    }

    @GetMapping("problem/{pageIndex}/{pageSize}")
    public R findProblemFeedbackList(@PathVariable(value = "pageIndex") Integer pageIndex, @PathVariable(value = "pageSize") Integer pageSize) {
        return problemFeedbackService.listProblemFeedbackPage(pageIndex, pageSize);
    }

    @PostMapping("problem/notice")
    @ApiOperation(value = "通知用户问题解决")
    public R noticeUser(@RequestBody ProblemFeedbackVo problemFeedbackVo) {
        return problemFeedbackService.noticeUserProblemIsOk(problemFeedbackVo);
    }

    @Autowired
    private SuggestFeedbackService suggestFeedbackService;

    /**
     * 建议反馈
     */
    @DeleteMapping("suggest/{id}")
    public R deleteSuggest(@PathVariable(value = "id") Integer id) {
        return suggestFeedbackService.deleteById(id);
    }

    @GetMapping("suggest/{pageIndex}/{pageSize}")
    public R findSuggestList(@PathVariable(value = "pageIndex") Integer pageIndex,
                             @PathVariable(value = "pageSize") Integer pageSize) {
        return suggestFeedbackService.listSuggestPage(pageIndex, pageSize);
    }
}
