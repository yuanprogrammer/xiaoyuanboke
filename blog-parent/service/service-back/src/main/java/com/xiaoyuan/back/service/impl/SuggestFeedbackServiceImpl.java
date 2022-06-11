package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.SuggestFeedbackMapper;
import com.xiaoyuan.back.db.entity.SuggestFeedback;
import com.xiaoyuan.back.service.SuggestFeedbackService;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
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
    public R listSuggestPage(Integer pageIndex, Integer pageSize) {
        Page<SuggestFeedback> page = new Page<>(pageIndex, pageSize);

        IPage<SuggestFeedback> iPage = this.baseMapper.selectPage(page, null);

        PageUtils pageUtils = new PageUtils(iPage.getRecords(), iPage.getTotal(), pageIndex, pageSize);
        return R.ok().data("suggestFeedbackList", pageUtils);
    }

    @Override
    public R deleteById(Integer id) {
        return this.baseMapper.deleteById(id) == 0 ? R.error().message("删除失败") : R.ok();
    }
}
