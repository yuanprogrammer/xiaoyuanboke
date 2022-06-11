package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.UserOperationService;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName:    UserOperationController
 * Author:      小袁教程
 * Date:        2022/6/7 12:40
 * Description:
 */
@RestController
@RequestMapping("/userOperation")
public class UserOperationController {

    @Autowired
    private UserOperationService userOperationService;

    @GetMapping("/type")
    public R selectUserOperationTypeList() {
        return userOperationService.selectOperationType();
    }
}
