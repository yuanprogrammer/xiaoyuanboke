package com.xiaoyuan.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    LoginController
 * Author:      小袁
 * Date:        2022/5/1 16:40
 * Description:
 */
@Controller
public class LoginController {

    @GetMapping(value = "login", produces = "text/html;charset=utf-8")
    public String toLoginPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("loginStatus", "1");
        return "login";
    }
}
