package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.entity.SuggestFeedback;
import com.xiaoyuan.model.vo.R;

/**
 * FileName:    SuggestFeedbackService
 * Author:      小袁
 * Date:        2022/4/28 19:11
 * Description:
 */
public interface SuggestFeedbackService extends IService<SuggestFeedback> {

    /**
     * 查询所有建议列表
     * @return
     */
    R listSuggestPage(Integer pageIndex, Integer pageSize);

    /**
     * 删除
     * @param id
     * @return
     */
    R deleteById(Integer id);
}
