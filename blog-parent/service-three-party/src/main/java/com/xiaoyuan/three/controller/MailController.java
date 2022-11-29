package com.xiaoyuan.three.controller;

import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.mail.SendMailParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.three.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/mailServer")
@CrossOrigin
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendOne")
    public R sendOneMail(@RequestBody SendMailParam sendMailParam) {
        try {
            mailService.sendSampleMail(sendMailParam.getTo(), sendMailParam.getTheme(), sendMailParam.getContent(), sendMailParam.getCc());
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return R.fail(HttpStatusEnum.MAIL_SEND_ERROR);
        }

        return R.success("发送成功");
    }
}
