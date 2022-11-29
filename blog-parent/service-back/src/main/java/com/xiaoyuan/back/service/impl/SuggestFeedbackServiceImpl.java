package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.SuggestFeedbackMapper;
import com.xiaoyuan.back.service.SuggestFeedbackService;
import com.xiaoyuan.model.entity.SuggestFeedback;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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

        HashMap<String, Object> map = new HashMap<>();
        map.put("suggestFeedbackList", pageUtils);
        return R.success(map);
    }

    @Override
    public R deleteById(Integer id) {
        return this.baseMapper.deleteById(id) == 0 ? R.fail("删除失败") : R.success();
    }
}
