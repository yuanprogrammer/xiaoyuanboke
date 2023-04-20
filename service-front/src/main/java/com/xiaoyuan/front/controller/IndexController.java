package com.xiaoyuan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    IndexController
 * Author:      小袁
 * Date:        2022/1/21 19:45
 * Description: 首页控制层
 */
@Controller
@RequestMapping(value = {"/", "/index", "/index"})
public class IndexController {

    @GetMapping(produces = "text/html;charset=utf-8")
    public String toIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
