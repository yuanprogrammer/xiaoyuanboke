package com.xiaoyuan.front.controller;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.front.annotation.CookieOperation;
import com.xiaoyuan.front.service.CommonService;
import com.xiaoyuan.front.vo.param.LoginParam;
import com.xiaoyuan.front.vo.param.MailParam;
import com.xiaoyuan.utils.constant.CookieConstant;
import com.xiaoyuan.utils.constant.RedisConstantKey;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                           @RequestBody String emailJson) {
        return commonService.sendEmailCode(permissionCode, emailJson);
    }

    @PostMapping("code/sms")
    @ApiOperation(value = "发送短信验证码（10分钟有效期）")
    public R sendSmsCode(@CookieValue(value = RedisConstantKey.MOBILE_NUMBER_REQUEST_VERIFY) String permissionCode,
                         @RequestBody String phoneJson) {
        return commonService.sendSmsCode(permissionCode, phoneJson);
    }

    @PostMapping("code/sms/request")
    @CookieOperation(phonePermission =  true)
    public R getRequestCode(@RequestBody String phoneJson) {
        return commonService.getRequestPermissionCode(phoneJson);
    }

    @PostMapping("code/email/request")
    @CookieOperation(emailPermission = true)
    public R getEMailCodePermission(@RequestBody String emailJson) {
        return commonService.getEMailCodePermission(emailJson);
    }
}