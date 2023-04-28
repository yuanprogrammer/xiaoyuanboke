package com.xiaoyuan.common.vo.article;

import lombok.Data;

/**
 * FileName:    ArticleCommentVo
 * Author:      小袁
 * Date:        2022/5/3 20:17
 * Description:
 */
@Data
public class ArticleCommentVo {

    private String number;

    private String avatar;

    private String nickname;

    private String content;

    /**
     * 是否当前登录本人的评论
     * 0 -> 不是或者当前登录无账号
     * 1 -> 是本人, 显示编辑和删除操作
     */
    private String status;

    private String commentTime;
}
