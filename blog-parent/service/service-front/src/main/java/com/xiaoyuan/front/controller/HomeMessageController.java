package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.HomeMessageService;
import com.xiaoyuan.front.vo.param.HomeMessageParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName:    HomeMessageController
 * Author:      小袁
 * Date:        2022/4/27 21:20
 * Description:
 */
@RestController
@RequestMapping("/homeMessage")
public class HomeMessageController {

    @Autowired
    private HomeMessageService homeMessageService;

    @PostMapping("insert")
    public R insertHomeMessage(@RequestBody HomeMessageParam homeMessageParam) {
        return homeMessageService.insert(homeMessageParam);
    }

    @PostMapping("list")
    public R findHomeMessageList(@RequestBody PageUtils pageUtils) {
        return homeMessageService.listHomeMessagePage(pageUtils);
    }
}
