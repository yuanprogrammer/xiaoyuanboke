package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.db.entity.HomeMessage;
import com.xiaoyuan.back.service.HomeMessageService;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName:    HomeMessageController
 * Author:      小袁
 * Date:        2022/5/1 0:37
 * Description:
 */
@RestController
@RequestMapping("/homeMessage")
public class HomeMessageController {

    @Autowired
    private HomeMessageService homeMessageService;

    @DeleteMapping("{id}")
    public R deleteHomeMessage(@PathVariable(value = "id") String id) {
        return homeMessageService.delete(id);
    }

    @DeleteMapping("")
    public R deleteMoreHomeMessage(@RequestBody List<String> ids) {
        return homeMessageService.deleteMore(ids);
    }

    @PutMapping("")
    public R modifyHomeMessage(@RequestBody HomeMessage homeMessage) {
        return homeMessageService.modify(homeMessage);
    }

    @GetMapping("{pageIndex}/{pageSize}")
    public R findHomeMessageList(@PathVariable(value = "pageIndex") Integer pageIndex,
                                 @PathVariable(value = "pageSize") Integer pageSize) {
        return homeMessageService.listHomeMessagePage(pageIndex, pageSize);
    }
}
