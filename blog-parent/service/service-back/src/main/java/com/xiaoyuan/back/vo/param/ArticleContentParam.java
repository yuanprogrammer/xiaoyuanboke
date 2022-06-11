package com.xiaoyuan.back.vo.param;

import lombok.Data;

/**
 * FileName:    ArticleContentParam
 * Author:      小袁
 * Date:        2022/4/16 11:43
 * Description:
 */
@Data
public class ArticleContentParam {

    /**
     * markdown文本
     */
    private String content;

    /**
     * html格式文本
     */
    private String contentHtml;
}
