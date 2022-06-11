package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.CommonUserService;
import com.xiaoyuan.back.service.UserOperationService;
import com.xiaoyuan.back.vo.param.UserOperationParam;
import com.xiaoyuan.back.vo.param.UserQueryParam;
import com.xiaoyuan.common.service.annotation.MultiRequestBody;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName:    CommonUserController
 * Author:      小袁教程
 * Date:        2022/6/6 21:16
 * Description:
 */
@RestController
@RequestMapping("/user")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @Autowired
    private UserOperationService userOperationService;

    @PostMapping("list")
    public R selectUserList(@MultiRequestBody PageUtils pageUtils,
                            @MultiRequestBody(required = false) UserQueryParam userQueryParam) {
        return commonUserService.selectUserList(pageUtils, userQueryParam);
    }

    @PostMapping("list/operation")
    public R selectUserOperationList(@MultiRequestBody PageUtils pageUtils,
                                     @MultiRequestBody(required = false) UserOperationParam userOperationParam) {
        return userOperationService.selectOperationList(pageUtils, userOperationParam);
    }
}
