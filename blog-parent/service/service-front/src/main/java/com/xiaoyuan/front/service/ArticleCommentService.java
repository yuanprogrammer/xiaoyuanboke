package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.ArticleComment;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    ArticleCommentService
 * Author:      小袁
 * Date:        2022/5/3 19:30
 * Description:
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    /**
     * 插入一条评论
     * @param articleCommentParam
     * @return
     */
    R insert(ArticleCommentParam articleCommentParam);

    /**
     * 删除评论
     * @param token
     * @return
     */
    R delete(String token, String number);

    /**
     * 修改评论内容
     * @return
     */
    R modify(String token, ArticleCommentParam articleCommentParam);

    /**
     * 查询文章评论
     * @param pageUtils
     * @return
     */
    R listCommentPage(String token, PageUtils pageUtils, String articleNumber);
}
