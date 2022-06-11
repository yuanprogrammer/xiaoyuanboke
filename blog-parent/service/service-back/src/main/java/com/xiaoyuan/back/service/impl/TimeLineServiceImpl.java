package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.TimeLineMapper;
import com.xiaoyuan.back.db.entity.TimeLine;
import com.xiaoyuan.back.service.TimeLineService;
import com.xiaoyuan.back.vo.TimeLineVo;
import com.xiaoyuan.utils.DateConverterUtil;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName:    TimeLineServiceImpl
 * Author:      小袁
 * Date:        2022/4/20 12:27
 * Description:
 */
@Service
@Transactional
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, TimeLine> implements TimeLineService {

    @Autowired
    private TimeLineMapper timeLineMapper;

    @Override
    public R insert(TimeLineVo timeLineVo) {
        TimeLine timeLine = new TimeLine();
        timeLine.setTitle(timeLineVo.getTitle());
        timeLine.setDescribe(timeLineVo.getDescribe());
        timeLine.setContent(timeLineVo.getContent());
        timeLine.setStartTime(DateConverterUtil.toDateFromString(timeLineVo.getStartTime()));
        timeLine.setEndTime(DateConverterUtil.toDateFromString(timeLineVo.getEndTime()));
        return timeLineMapper.insert(timeLine) == 0 ? R.error().message("添加失败") : R.ok().message("添加成功");
    }

    @Override
    public R modify(TimeLineVo timeLineVo) {
        TimeLine timeLine = new TimeLine();
        timeLine.setId(Long.parseLong(timeLineVo.getId()));
        timeLine.setTitle(timeLineVo.getTitle());
        timeLine.setDescribe(timeLineVo.getDescribe());
        timeLine.setContent(timeLineVo.getContent());
        timeLine.setStartTime(DateConverterUtil.toDateFromString(timeLineVo.getStartTime()));
        timeLine.setEndTime(DateConverterUtil.toDateFromString(timeLineVo.getEndTime()));
        return timeLineMapper.updateById(timeLine) == 0 ? R.error().message("编辑失败") : R.ok().message("编辑成功");
    }

    @Override
    public R delete(Long id) {
        return timeLineMapper.deleteById(id) == 0 ? R.error().message("删除失败") : R.ok().message("删除成功");
    }

    @Override
    public R listTimeLinePage(Integer pageIndex, Integer pageSize) {
        // 创建分页对象
        Page<TimeLine> page = new Page<>(pageIndex, pageSize);
        // 查询
        IPage<TimeLine> timeLineIPage = timeLineMapper.selectPage(page, null);
        // 回显对象
        List<TimeLineVo> lineVos = copyList(timeLineIPage.getRecords());
        return R.ok().data("timeLineList", new PageUtils(lineVos, timeLineIPage.getTotal(), pageIndex, pageSize));
    }

    @Override
    public R getTimeLineInfoById(Long id) {
        TimeLine timeLine = timeLineMapper.selectById(id);
        return timeLine == null ? R.error().message("该条时间线已不存在...") : R.ok().data("timeLine", copy(timeLine));
    }

    private TimeLineVo copy(TimeLine timeLine) {
        TimeLineVo timeLineVo = new TimeLineVo();
        // Long -->> String
        timeLineVo.setId(String.valueOf(timeLine.getId()));
        // 开始时间 和 结束时间
        timeLineVo.setStartTime(DateConverterUtil.toStringFromDate(timeLine.getStartTime()));
        timeLineVo.setEndTime(DateConverterUtil.toStringFromDate(timeLine.getEndTime()));
        // 拷贝剩下的
        BeanUtils.copyProperties(timeLine, timeLineVo);
        return timeLineVo;
    }

    private List<TimeLineVo> copyList(List<TimeLine> list) {
        List<TimeLineVo> lineVos = new ArrayList<>();
        for (TimeLine timeLine : list) {
            lineVos.add(copy(timeLine));
        }
        return lineVos;
    }
}
