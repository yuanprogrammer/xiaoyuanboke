package com.xiaoyuan.three.controller;

import com.xiaoyuan.model.param.SendSmsCodeParam;
import com.xiaoyuan.model.vo.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/smsServer")
public class SmsController {

    @PostMapping("sendOne")
    public R sendOneSms(@RequestBody @Valid SendSmsCodeParam sendSmsCodeParam) {
        return R.success();
    }
}
