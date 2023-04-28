package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.pojo.TimeLine;
import com.xiaoyuan.common.vo.PageUtils;
import com.xiaoyuan.common.vo.R;

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
