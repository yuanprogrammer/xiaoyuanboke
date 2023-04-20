package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.HomeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
 * FileName:    ArchiveController
 * Author:      小袁
 * Date:        2022/1/23 21:08
 * Description: 归档页面的控制层
 */
@Controller
@RequestMapping(value = "/archive")
public class ArchiveController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HomeMessageService homeMessageService;

    /**
     * 归档页面
     */
    @GetMapping(value = "/archive", produces = "text/html;charset=utf-8")
    public String toArchivePage(Model model, HttpServletRequest request, HttpServletResponse response) {
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

        return "archive/archive";
    }
}
