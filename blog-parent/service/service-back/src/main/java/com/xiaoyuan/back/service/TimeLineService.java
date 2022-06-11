package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.back.db.entity.TimeLine;
import com.xiaoyuan.back.vo.TimeLineVo;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    TimeLineService
 * Author:      小袁
 * Date:        2022/4/20 12:27
 * Description:
 */
public interface TimeLineService extends IService<TimeLine> {

    /**
     * 添加一条时间线
     * @param timeLineVo
     * @return
     */
    R insert(TimeLineVo timeLineVo);

    /**
     * 编辑时间线
     * @param timeLineVo
     * @return
     */
    R modify(TimeLineVo timeLineVo);

    /**
     * 删除时间线
     * @param id
     * @return
     */
    R delete(Long id);

    /**
     * 分页查询
     * @return
     */
    R listTimeLinePage(Integer pageIndex, Integer pageSize);

    /**
     * 通过ID查询时间线
     * @param id
     * @return
     */
    R getTimeLineInfoById(Long id);
}
