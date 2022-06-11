package com.xiaoyuan.front.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * FileName:    ArchiveVo
 * Author:      小袁
 * Date:        2022/4/24 20:32
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchivesVo {

    // 档案时间
    private String archivesTime;

    // 档案数
    private Integer count;

    // 文章列表
    private List<ArticleVo> articleList;
}
