package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.TimeLineService;
import com.xiaoyuan.common.vo.TimeLineVo;
import com.xiaoyuan.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    TimeLineController
 * Author:      小袁
 * Date:        2022/4/20 12:53
 * Description:
 */
@RestController
@RequestMapping(value = "/timeline")
public class TimeLineController {

    @Autowired
    private TimeLineService timeLineService;

    @PostMapping("")
    public R insertTimeLine(@RequestBody TimeLineVo timeLineVo) {
        return timeLineService.insert(timeLineVo);
    }

    @DeleteMapping("{id}")
    public R deleteTimeLine(@PathVariable(value = "id") Long id) {
        return timeLineService.delete(id);
    }

    @PutMapping("")
    public R modifyTimeLine(@RequestBody TimeLineVo timeLineVo) {
        return timeLineService.modify(timeLineVo);
    }

    @GetMapping("{id}")
    public R getTimeLineInfo(@PathVariable(value = "id") String id) {
        return timeLineService.getTimeLineInfoById(Long.parseLong(id));
    }

    @GetMapping("")
    public R findTimeLineList() {
        return timeLineService.listTimeLinePage(1, 10);
    }

    @GetMapping("{pageIndex}/{pageSize}")
    public R findTimeLineList(@PathVariable(value = "pageIndex") Integer pageIndex, @PathVariable(value = "pageSize") Integer pageSize) {
        if (pageSize >= 20) {
            return R.fail("请求数据过多.......拒绝请求！");
        }
        return timeLineService.listTimeLinePage(pageIndex, pageSize);
    }
}
