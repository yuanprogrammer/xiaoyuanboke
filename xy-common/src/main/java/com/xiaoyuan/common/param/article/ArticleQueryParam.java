package com.xiaoyuan.common.param.article;

import com.xiaoyuan.common.vo.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleQueryParam extends Pagination {

    /**
     * 文章编号
     */
    private String id;

    private String author;

    private String tag;

    private String title;

    private String sort;

    private String category;

    private String startTime;

    private String endTime;

    /**
     * 搜索词
     */
    private String searchWord;

    /**
     * 排序条件
     */
    private String sortCondition;

    /**
     * 是否管理员
     */
    private String isAdmin;
}
