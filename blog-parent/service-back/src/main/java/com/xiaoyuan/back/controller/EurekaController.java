package com.xiaoyuan.back.controller;

import com.xiaoyuan.model.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaController {


    @GetMapping("/actuator/info")
    public R getEurekaInfo() {
        return R.success();
    }
}
