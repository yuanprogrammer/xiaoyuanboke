package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.ProblemFeedbackMapper;
import com.xiaoyuan.back.db.entity.ProblemFeedback;
import com.xiaoyuan.back.service.ProblemFeedbackService;
import com.xiaoyuan.back.service.ThreadService;
import com.xiaoyuan.back.vo.ProblemFeedbackVo;
import com.xiaoyuan.common.service.MailService;
import com.xiaoyuan.utils.DateConverterUtil;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
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
        return this.baseMapper.deleteById(id) == 0 ? R.error().message("删除失败") : R.ok();
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

        return this.baseMapper.updateById(problemFeedback) == 0 ? R.error().message("修改失败") : R.ok();
    }

    @Override
    public R listProblemFeedbackPage(Integer pageIndex, Integer pageSize) {
        // 分页对象
        Page<ProblemFeedback> page = new Page<>(pageIndex, pageSize);
        IPage<ProblemFeedback> problemFeedbackIPage = this.baseMapper.selectPage(page, null);

        // 装入分页工具类中, List列表数据, Total总记录数, pageIndex当前页, pageSize页大小
        PageUtils pageUtils = new PageUtils(copyList(problemFeedbackIPage.getRecords()), problemFeedbackIPage.getTotal(), pageIndex, pageSize);
        return R.ok().data("problemFeedbackList", pageUtils);
    }

    @Override
    public R noticeUserProblemIsOk(ProblemFeedbackVo problemFeedbackVo) {
        threadService.sendProblemSolveNoticeToUser(problemFeedbackVo);
        ProblemFeedback problemFeedback = new ProblemFeedback();
        problemFeedback.setId(Long.parseLong(problemFeedbackVo.getId()));
        problemFeedback.setNoticeState('1');
        return this.baseMapper.updateById(problemFeedback) == 0 ? R.error().message("通知失败") : R.ok();
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
