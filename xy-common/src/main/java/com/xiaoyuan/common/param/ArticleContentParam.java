package com.xiaoyuan.common.param;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    ArticleContentParam
 * Author:      小袁
 * Date:        2022/4/16 11:43
 * Description:
 */
@Data
public class ArticleContentParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * markdown文本
     */
    private String content;

    /**
     * html格式文本
     */
    private String contentHtml;
}
