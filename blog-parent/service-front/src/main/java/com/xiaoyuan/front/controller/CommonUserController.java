package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.annotation.CookieOperation;
import com.xiaoyuan.front.service.CommonUserService;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.CodeParam;
import com.xiaoyuan.front.vo.param.LoginParam;
import com.xiaoyuan.model.constants.CookieConstant;
import com.xiaoyuan.model.param.comuser.FindPasswordParam;
import com.xiaoyuan.model.param.comuser.ModifyPasswordParam;
import com.xiaoyuan.model.param.comuser.RegisterParam;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * FileName:    CommonUserController
 * Author:      小袁
 * Date:        2022/5/1 16:38
 * Description:
 */
@RestController
@RequestMapping("/user")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @PostMapping("login")
    @CookieOperation(userToken = true)
    public R login(@RequestBody @Valid LoginParam loginParam) {
        return commonUserService.login(loginParam);
    }

    @PostMapping("register")
    @CookieOperation(userToken = true)
    public R register(@RequestBody @Valid RegisterParam registerParam) {
        return commonUserService.register(registerParam);
    }

    @PostMapping("info")
    public R getUserInfo(@CookieValue(value = CookieConstant.TOKEN, required = false) String token) {
        return commonUserService.getUserInfo(token);
    }

    @PostMapping("logout")
    @CookieOperation(delUserToken = true)
    public R logout(@CookieValue(value = CookieConstant.TOKEN) String token) {
        // 清除redis数据
        return commonUserService.logout(token);
    }

    // 修改密码
    @PutMapping("password/modify")
    public R modifyPassword(@RequestBody @Valid ModifyPasswordParam modifyPasswordParam) {
        return commonUserService.modifyPassword(modifyPasswordParam);
    }

    // 修改昵称或用户名（用户名未设置下）
    @PutMapping("nickname/modify")
    public R modifyNickname(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                            @RequestBody CommonUserVo commonUserVo) {
        return commonUserService.modifyNickname(token, commonUserVo);
    }

    // 修改邮箱或激活邮箱
    @PutMapping("modify/email")
    public R modifyEmail(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                         @RequestBody(required = false) CodeParam codeParam) {
        return commonUserService.modifyEmail(token, codeParam);
    }

    @PutMapping("modify/mobileNumber")
    public R modifyMobileNumber(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                                @RequestBody(required = false) CodeParam codeParam) {
        return commonUserService.modifyMobileNumber(token, codeParam);
    }

    @PutMapping("password/find")
    @ApiOperation("找回密码")
    public R findPassword(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                          @RequestBody @Valid FindPasswordParam findPasswordParam) {
        return commonUserService.findPassword(token, findPasswordParam);
    }
}
