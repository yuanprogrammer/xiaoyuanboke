package com.xiaoyuan.model.feign;

import com.xiaoyuan.model.param.SendSmsCodeParam;
import com.xiaoyuan.model.param.mail.SendMailParam;
import com.xiaoyuan.model.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "threePartyServer", path = "/common/api/")
public interface ThreePartyFeign {

    @PostMapping("/mailServer/sendOne")
    R sendOneMail(@RequestBody SendMailParam sendMailParam);

    @PostMapping("/smsServer/sendOne")
    R sendOneSms(@RequestBody @Valid SendSmsCodeParam sendSmsCodeParam);
}
