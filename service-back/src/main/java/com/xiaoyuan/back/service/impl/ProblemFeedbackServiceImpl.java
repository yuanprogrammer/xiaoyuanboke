package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.ProblemFeedbackMapper;
import com.xiaoyuan.back.service.ProblemFeedbackService;
import com.xiaoyuan.back.service.ThreadService;
import com.xiaoyuan.common_util.convert.DateConverterUtil;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.ProblemFeedback;
import com.xiaoyuan.model.param.problem.ProblemQueryParam;
import com.xiaoyuan.model.vo.ProblemFeedbackVo;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName:    ProblemFeedbackServiceImpl
 * Author:      小袁
 * Date:        2022/4/21 8:48
 * Description:
 */
@Service
@Transactional
public class ProblemFeedbackServiceImpl extends ServiceImpl<ProblemFeedbackMapper, ProblemFeedback> implements ProblemFeedbackService {

    @Autowired
    private ThreadService threadService;

    @Override
    public R delete(Long id) {
        // 删除
        return this.baseMapper.deleteById(id) == 0 ? R.fail("删除失败") : R.success();
    }

    @Override
    public R modifyProblemState(ProblemFeedbackVo problemFeedbackVo) {
        ProblemFeedback problemFeedback = new ProblemFeedback();
        problemFeedback.setId(Long.parseLong(problemFeedbackVo.getId()));
        if (problemFeedbackVo.getProblemState() != null) {
            problemFeedback.setProblemState(problemFeedbackVo.getProblemState());
        }
        if (problemFeedbackVo.getNoticeState() != null) {
            problemFeedback.setNoticeState(problemFeedbackVo.getNoticeState());
        }

        return this.baseMapper.updateById(problemFeedback) == 0 ? R.fail("修改失败") : R.success();
    }

    @Override
    public R listProblemFeedbackPage(ProblemQueryParam problemQueryParam) {
        IPage<ProblemFeedbackVo> problemPage = this.baseMapper.listProblemFeedbackPage(new Page<>(problemQueryParam.getPageIndex(), problemQueryParam.getPageSize()), problemQueryParam);

        return R.success(new PageVo<>(problemPage.getRecords(), problemPage.getTotal()));
    }

    @Override
    public R noticeUserProblemIsOk(ProblemFeedbackVo problemFeedbackVo) {
        // 发送http请求
        String content = "编号：" + problemFeedbackVo.getId() + "\n"
                + "\n" + "您所反馈的问题：" + problemFeedbackVo.getProblem() + "\n"
                + "\n" + "感谢您的反馈 ❤";

        threadService.sendSimpleMail(problemFeedbackVo.getEmail(), "您反馈的问题已经解决", content);

        ProblemFeedback problemFeedback = new ProblemFeedback();
        problemFeedback.setId(Long.parseLong(problemFeedbackVo.getId()));
        problemFeedback.setNoticeState('1');
        return this.baseMapper.updateById(problemFeedback) == 0 ? R.fail("通知失败") : R.success();
    }

    /**
     * 拷贝, Entity -->> Vo
     * @param problemFeedback 原对象
     * @return Vo对象
     */
    private ProblemFeedbackVo copy(ProblemFeedback problemFeedback) {
        ProblemFeedbackVo problemFeedbackVo = new ProblemFeedbackVo();
        problemFeedbackVo.setId(String.valueOf(problemFeedback.getId()));
        problemFeedbackVo.setEmail(problemFeedback.getEmail());
        problemFeedbackVo.setProblem(problemFeedback.getProblem());
        problemFeedbackVo.setProblemState(problemFeedback.getProblemState());
        problemFeedbackVo.setNoticeState(problemFeedback.getNoticeState());
        problemFeedbackVo.setCreateTime(DateConverterUtil.toStringFromDate(problemFeedback.getGmtCreate()));

        return problemFeedbackVo;
    }

    private List<ProblemFeedbackVo> copyList(List<ProblemFeedback> list) {
        List<ProblemFeedbackVo> vos = new ArrayList<>();
        // lambda表达式
        list.forEach(item -> {
            vos.add(copy(item));
        });

        return vos;
    }
}
