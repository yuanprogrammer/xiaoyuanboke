package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.StrUtil;
import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.back.service.SchedulerService;
import com.xiaoyuan.common_util.annotation.IsAdmin;
import com.xiaoyuan.model.param.ArticleParam;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    ArticleController
 * Author:      小袁
 * Date:        2022/4/16 12:54
 * Description: 文章控制层
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章管理控制层")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("publish")
    @SaCheckPermission(value = {"ARTICLE:PUBLISH"}, mode = SaMode.OR)
    public R publishArticle(@RequestBody ArticleParam articleParam) {
        if (StrUtil.isEmpty(articleParam.getPublishTime())) {
            return articleService.insert(articleParam);
        }else {
            return schedulerService.publishArticle(articleParam);
        }
    }

    @DeleteMapping("{articleId}")
    @SaCheckPermission(value = {"ARTICLE:DELETE"}, mode = SaMode.OR)
    public R removeArticle(@PathVariable(value = "articleId") String articleId) {
        return articleService.removeArticleById(Long.parseLong(articleId));
    }

    @PutMapping("")
    @SaCheckPermission(value = {"ARTICLE:UPDATE"}, mode = SaMode.OR)
    public R modifyArticle(@RequestBody ArticleParam articleParam) {
        return articleService.modifyArticleById(articleParam);
    }

    @ApiOperation(value = "分页查询文章")
    @PostMapping("list")
    @SaCheckPermission(value = {"ARTICLE:SELECT"}, mode = SaMode.OR)
    public R findArticleList(@IsAdmin ArticleQueryParam articleQueryParam) {
        return articleService.listArticlePage(articleQueryParam);
    }

    /**
     * 获取文章详细内容
     */
    @GetMapping("detail/{articleId}")
    @SaCheckPermission(value = {"ARTICLE:SELECT"}, mode = SaMode.OR)
    public R getArticleContent(@PathVariable(value = "articleId") String articleId) {
        return articleService.getArticleDetailById(Long.parseLong(articleId));
    }

    @GetMapping("{articleId}")
    @SaCheckPermission(value = {"ARTICLE:SELECT"}, mode = SaMode.OR)
    public R getArticlePublish(@PathVariable(value = "articleId") String articleId) {
        return articleService.getArticlePublishById(Long.parseLong(articleId));
    }
}
