package com.xiaoyuan.front.controller;

import com.xiaoyuan.front.service.ArticleCollectService;
import com.xiaoyuan.front.service.ArticleLikeService;
import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.model.constants.CookieConstant;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.param.article.CategoryQueryParam;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * FileName:    ArticleController
 * Author:      小袁
 * Date:        2022/4/24 17:48
 * Description:
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCollectService articleCollectService;

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("list")
    public R findArticleList(@RequestBody ArticleQueryParam articleQueryParam) {
        return articleService.listPageArticle(articleQueryParam);
    }

    @PostMapping("category/list")
    public R findCategoryArticleList(@RequestBody CategoryQueryParam categoryQueryParam) {
        return articleService.listCategoryArticleList(categoryQueryParam);
    }

    @PostMapping("archives/list")
    public R findArchivesArticleList() {
        return articleService.listArchivesArticle();
    }

    @GetMapping("detail/{number}")
    public R getArticleDetail(@PathVariable(value = "number") String number) {
        return articleService.getArticleDetailById(number);
    }

    @GetMapping("markdown/{number}")
    public R getArticleMarkdown(@PathVariable(value = "number") String number) {
        return articleService.getArticleMarkdownById(number);
    }

    @PostMapping("like")
    @ApiOperation("文章点赞")
    public R updateArticleGoodCount(@RequestBody @Valid ArticleLikeParam articleLikeParam) {
        return articleLikeService.likeArticle(articleLikeParam);
    }

    @PostMapping("collect")
    @ApiOperation("文章收藏")
    public R collectArticle(@RequestBody @Valid ArticleLikeParam articleLikeParam) {
        return articleCollectService.collectArticle(articleLikeParam);
    }

    @PostMapping("like/info")
    @ApiOperation("获取文章点赞信息")
    public R getLikeInfo(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                         @RequestBody @Valid ArticleLikeParam articleLikeParam) {
        return articleLikeService.getLikeInfo(token, articleLikeParam);
    }

    @PostMapping("collect/info")
    @ApiOperation("获取文章收藏信息")
    public R getCollectInfo(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                            @RequestBody @Valid ArticleLikeParam articleLikeParam) {
        return articleCollectService.getCollectInfo(token, articleLikeParam);
    }
}
