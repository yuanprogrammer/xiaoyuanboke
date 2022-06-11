package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.FriendLinkService;
import com.xiaoyuan.front.service.HomeMessageService;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    FriendLinkController
 * Author:      小袁
 * Date:        2022/4/30 12:07
 * Description:
 */
@Controller
@RequestMapping("/friendlink")
public class FriendLinkController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FriendLinkService friendLinkService;

    @Autowired
    private HomeMessageService homeMessageService;

    //引用redis 缓存页面
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //手动渲染前端页面，视图解析器
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @GetMapping(value = "friendLink", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toFriendLinkPage(Model model, HttpServletRequest request, HttpServletResponse response) {
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
        // 留言数量
        model.addAttribute("messageTotal", homeMessageService.findMessageTotal());

        //Redis中获取页面，如果不为空 直接返回页面

        String HTML = redisTemplate.opsForValue().get("friendLink");
        if (!StringUtils.isEmpty(HTML)) {
            return HTML;
        }

        //如果为空 渲染页面 并且存入redis
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //去渲染页面 页面需要模板的名称 用来以后调用  还需要IContext 上面获得IContext 传入
        HTML = thymeleafViewResolver.getTemplateEngine().process("friendlink/friendLink", webContext);
        if (!StringUtils.isEmpty(HTML)) {
            // 丢入redis缓存
            redisTemplate.opsForValue().set("friendLink", HTML);
        }
        return HTML;
    }

    @GetMapping("list")
    @ResponseBody
    public R findFriendLinkList() {
        return friendLinkService.findFriendLinkAll();
    }
}
