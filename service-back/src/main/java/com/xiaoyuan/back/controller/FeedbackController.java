package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.xiaoyuan.back.service.ProblemFeedbackService;
import com.xiaoyuan.back.service.SuggestFeedbackService;
import com.xiaoyuan.model.param.problem.ProblemQueryParam;
import com.xiaoyuan.model.vo.ProblemFeedbackVo;
import com.xiaoyuan.model.vo.R;
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
    @SaCheckPermission(value = {"PROBLEM:DELETE"}, mode = SaMode.OR)
    public R deleteProblem(@PathVariable(value = "id") String id) {
        return problemFeedbackService.delete(Long.parseLong(id));
    }

    @PutMapping("problem")
    @ApiOperation(value = "编辑问题状态")
    @SaCheckPermission(value = {"PROBLEM:UPDATE"}, mode = SaMode.OR)
    public R modifyProblemState(@RequestBody ProblemFeedbackVo problemFeedbackVo) {
        return problemFeedbackService.modifyProblemState(problemFeedbackVo);
    }

    @PostMapping("problem/list")
    @ApiOperation(value = "查询问题列表")
    @SaCheckPermission(value = {"PROBLEM:SELECT"}, mode = SaMode.OR)
    public R findProblemFeedbackList(@RequestBody ProblemQueryParam queryParam) {
        return problemFeedbackService.listProblemFeedbackPage(queryParam);
    }

    @PostMapping("problem/notice")
    @ApiOperation(value = "通知用户问题解决")
    @SaCheckPermission(value = {"PROBLEM:NOTICE"}, mode = SaMode.OR)
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
