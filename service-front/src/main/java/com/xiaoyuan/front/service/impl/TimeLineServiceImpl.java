package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common.util.DateConverterUtil;
import com.xiaoyuan.front.mapper.TimeLineMapper;
import com.xiaoyuan.front.service.TimeLineService;
import com.xiaoyuan.common.pojo.TimeLine;
import com.xiaoyuan.common.vo.TimeLineVo;
import com.xiaoyuan.common.vo.PageUtils;
import com.xiaoyuan.common.vo.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:    TimeLineServiceImpl
 * Author:      小袁
 * Date:        2022/4/23 23:38
 * Description:
 */
@Service
@Transactional
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, TimeLine> implements TimeLineService {
    @Override
    public R findTimeLineList(PageUtils pageUtils) {
        int pageIndex = pageUtils.getPageIndex();
        int pageSize = pageUtils.getPageSize();
        // 创建分页对象
        Page<TimeLine> page = new Page<>(pageIndex, pageSize);
        // 构建条件查询 排序条件 endTime
        QueryWrapper<TimeLine> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("end_time");
        // 调用父类的mapper中的方法 查询
        IPage<TimeLine> timeLineIPage = this.baseMapper.selectPage(page, wrapper);
        List<TimeLine> timeLines = timeLineIPage.getRecords();
        // 构件自定义分页对象
        pageUtils = new PageUtils(copyList(timeLines), timeLineIPage.getTotal(), pageIndex, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("timeLineList", pageUtils);

        return R.success(map);
    }

    private TimeLineVo copy(TimeLine timeLine) {
        TimeLineVo timeLineVo = new TimeLineVo();
        timeLineVo.setTitle(timeLine.getTitle());
        timeLineVo.setDescribe(timeLine.getDescribe());
        timeLineVo.setContent(timeLine.getContent());
        timeLineVo.setStartTime(DateConverterUtil.toStringFromDate(timeLine.getStartTime()));
        timeLineVo.setEndTime(DateConverterUtil.toStringFromDate(timeLine.getEndTime()));

        return timeLineVo;
    }

    private List<TimeLineVo> copyList(List<TimeLine> timeLines) {
        // 最终前端回传对象
        List<TimeLineVo> timeLineVos = new ArrayList<>();
        // lambda表达式
        timeLines.forEach(item -> {
            timeLineVos.add(copy(item));
        });
        return timeLineVos;
    }
}
