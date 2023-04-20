package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.xiaoyuan.back.service.CommonUserService;
import com.xiaoyuan.back.service.UserOperationService;
import com.xiaoyuan.model.param.UserOperationParam;
import com.xiaoyuan.model.param.UserQueryParam;
import com.xiaoyuan.model.param.sysuser.SysUserParam;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    @SaCheckPermission(value = {"USER:SELECT"}, mode = SaMode.OR)
    public R selectUserList(@RequestBody UserQueryParam userQueryParam) {
        return commonUserService.selectUserList(userQueryParam);
    }

    @PostMapping("pullBlack")
    @ApiOperation("加入黑名单")
    @SaCheckPermission(value = {"USER:DISABLE"}, mode = SaMode.OR)
    public R pullBlackById(@RequestBody @Valid SysUserParam sysUserParam) {
        return commonUserService.pullBlackById(sysUserParam);
    }

    @PostMapping("list/operation")
    @SaCheckPermission(value = {"USER:SELECT"}, mode = SaMode.OR)
    public R selectUserOperationList(@RequestBody UserOperationParam userOperationParam) {
        return userOperationService.selectOperationList(userOperationParam);
    }
}
