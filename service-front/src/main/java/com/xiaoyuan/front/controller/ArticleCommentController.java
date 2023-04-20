package com.xiaoyuan.front.controller;

import com.xiaoyuan.common_util.annotation.MultiRequestBody;
import com.xiaoyuan.front.service.ArticleCommentService;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.model.constants.CookieConstant;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.param.article.CommentDeleteParam;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
                           @RequestBody @Valid CommentDeleteParam commentDeleteParam) {
        return articleCommentService.delete(token, commentDeleteParam);
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
                               @RequestBody @Valid ArticleLikeParam articleLikeParam) {
        return articleCommentService.listCommentPage(token, articleLikeParam);
    }
}
