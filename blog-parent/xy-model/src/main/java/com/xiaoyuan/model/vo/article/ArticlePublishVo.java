package com.xiaoyuan.model.vo.article;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * FileName:    ArticlePublishVo
 * Author:      小袁
 * Date:        2022/4/19 16:47
 * Description:
 */
@Data
public class ArticlePublishVo implements Serializable {

    private final static long serialVersionUID = 1L;

    private String title;

    private String digest;

    private String cover;

    private String content;

    private String tags;

    private List<List<String>> categorySelected;
}
