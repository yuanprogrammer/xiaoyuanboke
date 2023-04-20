package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.HomeMessageService;
import com.xiaoyuan.front.service.TimeLineService;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    TimeLineController
 * Author:      小袁
 * Date:        2022/1/23 22:12
 * Description: 时间线页面的控制层
 */
@Controller
@RequestMapping(value = "/timeline")
public class TimeLineController {

    @Autowired
    private TimeLineService timeLineService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HomeMessageService homeMessageService;

    /**
     * 时间线页面
     */
    @GetMapping(value = "/timeLine", produces = "text/html;charset=utf-8")
    public String toTimeLinePage(Model model, HttpServletRequest request, HttpServletResponse response) {
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

        return "timeline/timeLine";
    }

    @PostMapping("/list")
    @ResponseBody
    public R findTimeLine(@RequestBody PageUtils pageUtils) {
        return timeLineService.findTimeLineList(pageUtils);
    }
}
