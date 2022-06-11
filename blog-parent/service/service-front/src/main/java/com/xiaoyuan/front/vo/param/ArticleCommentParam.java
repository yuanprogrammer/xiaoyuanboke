package com.xiaoyuan.front.vo.param;

import lombok.Data;

/**
 * FileName:    ArticleCommentParam
 * Author:      小袁
 * Date:        2022/5/3 19:31
 * Description:
 */
@Data
public class ArticleCommentParam {

    // 评论编号
    private String number;

    // 文章编号
    private String articleNumber;

    // 评论内容
    private String content;
}
