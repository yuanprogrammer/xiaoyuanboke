package com.xiaoyuan.front.controller;

import com.xiaoyuan.common.service.annotation.MultiRequestBody;
import com.xiaoyuan.utils.constant.CookieConstant;
import com.xiaoyuan.front.service.ArticleCommentService;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    ArticleCommentController
 * Author:      小袁
 * Date:        2022/5/3 20:27
 * Description:
 */
@RestController
@RequestMapping("/article/comment")
public class ArticleCommentController {

    @Autowired
    private ArticleCommentService articleCommentService;

    @PostMapping("")
    @ApiOperation(value = "插入一条评论")
    public R insertComment(@RequestBody ArticleCommentParam articleCommentParam) {
        return articleCommentService.insert(articleCommentParam);
    }

    @DeleteMapping("")
    @ApiOperation(value = "删除一条评论")
    public R deleteComment(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                           @MultiRequestBody(required = false) String number) {
        return articleCommentService.delete(token, number);
    }

    @PutMapping("")
    @ApiOperation(value = "修改评论内容")
    public R modifyComment(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                           @RequestBody ArticleCommentParam articleCommentParam) {
        return articleCommentService.modify(token, articleCommentParam);
    }

    @PostMapping("list")
    @ApiOperation(value = "查询文章评论列表")
    public R selectCommentList(@CookieValue(value = CookieConstant.TOKEN, required = false) String token,
                               @MultiRequestBody(value = "pageParam") PageUtils pageUtils,
                               @MultiRequestBody(required = false) String number) {
        return articleCommentService.listCommentPage(token, pageUtils, number);
    }
}
