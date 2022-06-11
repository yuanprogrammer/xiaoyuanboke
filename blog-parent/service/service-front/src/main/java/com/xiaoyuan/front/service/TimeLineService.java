package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.TimeLine;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    TimeLineService
 * Author:      小袁
 * Date:        2022/4/23 23:38
 * Description:
 */
public interface TimeLineService extends IService<TimeLine> {

    /**
     * 查询时间线
     * @param pageUtils
     * @return
     */
    R findTimeLineList(PageUtils pageUtils);
}
