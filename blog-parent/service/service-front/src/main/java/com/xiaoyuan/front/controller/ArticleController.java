package com.xiaoyuan.front.controller;

import com.xiaoyuan.common.service.annotation.MultiRequestBody;
import com.xiaoyuan.front.annotation.AddressRequestLimit;
import com.xiaoyuan.front.db.entity.ArticleCollect;
import com.xiaoyuan.front.service.ArticleCollectService;
import com.xiaoyuan.front.service.ArticleLikeService;
import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.vo.param.ArticleQueryParam;
import com.xiaoyuan.front.vo.param.ConditionParam;
import com.xiaoyuan.utils.constant.CookieConstant;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public R findArticleList(@MultiRequestBody(value = "pageParam") PageUtils pageUtils,
                             @MultiRequestBody(value = "articleQueryParam", required = false) ArticleQueryParam articleQueryParam) {
        return articleService.listPageArticle(pageUtils, articleQueryParam);
    }

    @PostMapping("category/list")
    public R findCategoryArticleList(@MultiRequestBody(value = "pageParam") PageUtils pageUtils,
                                     @MultiRequestBody(required = false) Integer categoryId) {
        return articleService.listCategoryArticleList(pageUtils, categoryId);
    }

    @PostMapping("archives/list")
    public R findArchivesArticleList() {
        return articleService.listArchivesArticle();
    }

    @PostMapping("detail/{number}")
    public R getArticleDetail(@PathVariable(value = "number") String number) {
        return articleService.getArticleDetailById(number);
    }

    @PostMapping("markdown/{number}")
    public R getArticleMarkdown(@PathVariable(value = "number") String number) {
        return articleService.getArticleMarkdownById(number);
    }

    @PostMapping("like")
    @ApiOperation("文章点赞")
    public R updateArticleGoodCount(@MultiRequestBody String number) {
        return articleLikeService.likeArticle(number);
    }

    @PostMapping("collect")
    @ApiOperation("文章收藏")
    public R collectArticle(@MultiRequestBody String number) {
        return articleCollectService.collectArticle(number);
    }

    @PostMapping("like/info")
    @ApiOperation("获取文章点赞信息")
    public R getLikeInfo(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                         @MultiRequestBody String number) {
        return articleLikeService.getLikeInfo(token, number);
    }

    @PostMapping("collect/info")
    @ApiOperation("获取文章收藏信息")
    public R getCollectInfo(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                            @MultiRequestBody String number) {
        return articleCollectService.getCollectInfo(token, number);
    }
}
