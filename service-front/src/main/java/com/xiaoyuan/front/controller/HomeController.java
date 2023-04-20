package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.HomeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * FileName:    HomeController
 * Author:      小袁
 * Date:        2022/4/26 0:16
 * Description:
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HomeMessageService homeMessageService;

//    //引用redis 缓存页面
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//    //手动渲染前端页面，视图解析器
//    @Autowired
//    private ThymeleafViewResolver thymeleafViewResolver;

    @GetMapping(value = "home", produces = "text/html;charset=utf-8")
    public String toHomePage(Model model, HttpServletRequest request, HttpServletResponse response) {
        // 热门文章
        model.addAttribute("hostArticleList", articleService.findHostsArticle());
        // 最新文章
        model.addAttribute("newArticleList", articleService.findNewsArticle());
        // 档案列表
        model.addAttribute("archivesList", articleService.findArchives());
        // 文章发布数量
        model.addAttribute("articleTotal", articleService.findTotal());
        // 文章浏览总量
        model.addAttribute("articleViewCount", articleService.findViewCount());
        // 评论数量
        model.addAttribute("messageTotal", homeMessageService.findMessageTotal());

        //Redis中获取页面，如果不为空 直接返回页面

//        String home = redisTemplate.opsForValue().get("home");
//        if (!StringUtils.isEmpty(home)) {
//            return home;
//        }
//
//        //如果为空 渲染页面 并且存入redis
//        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        //去渲染页面 页面需要模板的名称 用来以后调用  还需要IContext 上面获得IContext 传入
//        home = thymeleafViewResolver.getTemplateEngine().process("", webContext);
//        if (!StringUtils.isEmpty(home)) {
//            // 丢入redis缓存
//            redisTemplate.opsForValue().set("home", home);
//        }
//        return home;
        return "home/home";
    }

    @GetMapping(value = "article/detail/{number}", produces = "text/html;charset=utf-8")
    public String toArticleDetailPage(@PathVariable(value = "number") String number, Model model,
                                      HttpServletRequest request, HttpServletResponse response) {

        if (articleService.isExistById(number)) {
            // 热门文章
            model.addAttribute("hostArticleList", articleService.findHostsArticle());
            // 最新文章
            model.addAttribute("newArticleList", articleService.findNewsArticle());
            // 档案列表
            model.addAttribute("archivesList", articleService.findArchives());
            // 文章发布数量
            model.addAttribute("articleTotal", articleService.findTotal());
            // 文章浏览总量
            model.addAttribute("articleViewCount", articleService.findViewCount());
            // 文章编号
            model.addAttribute("number", number);
            // 评论数量
            model.addAttribute("messageTotal", homeMessageService.findMessageTotal());

//            //Redis中获取页面，如果不为空 直接返回页面
//            String HTML = redisTemplate.opsForValue().get("article:" + number);
//            if (!StringUtils.isEmpty(HTML)) {
//                return HTML;
//            }
//
//            //如果为空 手动渲染页面 并且存入redis
//            WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//            //去渲染页面 页面需要模板的名称 用来以后调用  还需要IContext 上面获得IContext 传入
//            HTML = thymeleafViewResolver.getTemplateEngine().process("home/article_detail", webContext);
//            if (!StringUtils.isEmpty(HTML)) {
//                // 丢入redis缓存
//                redisTemplate.opsForValue().set("article:" + number, HTML, 7 * 24 * 60 * 60L, TimeUnit.SECONDS);
//            }
//            return HTML;

            return "home/article_detail";
        }else {
//            WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//            //去渲染页面 页面需要模板的名称 用来以后调用  还需要IContext 上面获得IContext 传入
//            return thymeleafViewResolver.getTemplateEngine().process("error/404", webContext);

            return "error/404";
        }
    }
}
