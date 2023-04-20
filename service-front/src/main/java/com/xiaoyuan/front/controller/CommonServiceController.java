package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.annotation.CookieOperation;
import com.xiaoyuan.front.service.CommonService;
import com.xiaoyuan.model.constants.RedisConstantKey;
import com.xiaoyuan.model.param.mail.SendMailCodeParam;
import com.xiaoyuan.model.param.SendSmsCodeParam;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * FileName:    CommonServiceController
 * Author:      小袁
 * Date:        2022/5/2 11:45
 * Description:
 */
@RestController
@RequestMapping("/service")
public class CommonServiceController {

    @Autowired
    private CommonService commonService;

    @PostMapping("code/email")
    @ApiOperation(value = "发送邮箱验证码(5分钟有效期)")
    public R sendEmailCode(@CookieValue(value = RedisConstantKey.EMAIL_REQUEST_VERIFY) String permissionCode,
                           @RequestBody @Valid SendMailCodeParam sendMailCodeParam) {
        return commonService.sendEmailCode(permissionCode, sendMailCodeParam);
    }

    @PostMapping("code/sms")
    @ApiOperation(value = "发送短信验证码（10分钟有效期）")
    public R sendSmsCode(@CookieValue(value = RedisConstantKey.MOBILE_NUMBER_REQUEST_VERIFY) String permissionCode,
                         @RequestBody @Valid SendSmsCodeParam sendSmsCodeParam) {
        return commonService.sendSmsCode(permissionCode, sendSmsCodeParam);
    }

    @PostMapping("code/sms/request")
    @CookieOperation(phonePermission =  true)
    public R getRequestCode(@RequestBody @Valid SendSmsCodeParam sendSmsCodeParam) {
        return commonService.getRequestPermissionCode(sendSmsCodeParam);
    }

    @PostMapping("code/email/request")
    @CookieOperation(emailPermission = true)
    public R getEMailCodePermission(@RequestBody @Valid SendMailCodeParam sendMailCodeParam) {
        return commonService.getEMailCodePermission(sendMailCodeParam);
    }
}
