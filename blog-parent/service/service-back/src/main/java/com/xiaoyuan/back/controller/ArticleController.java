package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.back.vo.param.ArticleParam;
import com.xiaoyuan.utils.vo.R;
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

    @PostMapping("publish")
    public R publishArticle(@RequestBody ArticleParam articleParam) {
        return articleService.insert(articleParam);
    }

    @DeleteMapping("{articleId}")
    public R removeArticle(@PathVariable(value = "articleId") String articleId) {
        return articleService.removeArticleById(Long.parseLong(articleId));
    }

    @PutMapping("")
    public R modifyArticle(@RequestBody ArticleParam articleParam) {
//        return articleParam == null ? R.error().message("失败") : R.ok().message("成功");
        return articleService.modifyArticleById(articleParam);
    }

    @GetMapping("{pageIndex}/{pageSize}")
    @ApiOperation(value = "分页查询文章")
    public R findArticleList(@PathVariable(value = "pageIndex") Integer pageIndex, @PathVariable(value = "pageSize") Integer pageSize) {
        if (pageSize >= 20) {
            return R.error().message("请求数据过多.......拒绝请求！");
        }
        return articleService.listArticlePage(pageIndex, pageSize);
    }

    @GetMapping("")
    @ApiOperation(value = "分页查询文章")
    public R findArticleList() {
        return articleService.listArticlePage(1, 10);
    }

    /**
     * 获取文章详细内容
     * @param articleId
     * @return
     */
    @GetMapping("detail/{articleId}")
    public R getArticleContent(@PathVariable(value = "articleId") String articleId) {
        return articleService.getArticleDetailById(Long.parseLong(articleId));
    }

    @GetMapping("{articleId}")
    public R getArticlePublish(@PathVariable(value = "articleId") String articleId) {
        return articleService.getArticlePublishById(Long.parseLong(articleId));
    }
}
