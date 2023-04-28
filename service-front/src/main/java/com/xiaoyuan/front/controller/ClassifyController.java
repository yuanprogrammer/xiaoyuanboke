package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.CategoryService;
import com.xiaoyuan.front.service.HomeMessageService;
import com.xiaoyuan.common.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FileName:    ClassifyController
 * Author:      小袁
 * Date:        2022/1/23 22:08
 * Description: 分类页面的控制层
 */
@Controller
@RequestMapping(value = "/classify")
public class ClassifyController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HomeMessageService homeMessageService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类页面
     */
    @GetMapping(value = "/classify", produces = "text/html;charset=utf-8")
    public String toClassifyPage(Model model, HttpServletRequest request, HttpServletResponse response) {
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

        return "classify/classify";
    }

    /**
     * 查询所有分类的目录结构
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    @ApiOperation(value = "查询所有分类的目录结构")
    public R getCategoryList() {
        return categoryService.listCategory();
    }
}
