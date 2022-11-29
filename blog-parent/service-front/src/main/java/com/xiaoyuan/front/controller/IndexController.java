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

    //引用redis 缓存页面
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //手动渲染前端页面，视图解析器
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @GetMapping(produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        //Redis中获取页面，如果不为空 直接返回页面

        String index = redisTemplate.opsForValue().get("index");
        if (!StringUtils.isEmpty(index)) {
            return index;
        }

        //如果为空 渲染页面 并且存入redis
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //去渲染页面 页面需要模板的名称 用来以后调用  还需要IContext 上面获得IContext 传入
        index = thymeleafViewResolver.getTemplateEngine().process("index", webContext);
        if (!StringUtils.isEmpty(index)) {
            // 丢入redis缓存
            redisTemplate.opsForValue().set("index", index);
        }

        return index;
    }
}
