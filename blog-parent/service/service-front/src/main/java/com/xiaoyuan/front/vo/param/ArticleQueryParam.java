package com.xiaoyuan.front.vo.param;

import lombok.Data;

/**
 * FileName:    ArticleQueryParam
 * Author:      小袁
 * Date:        2022/4/26 16:55
 * Description:
 */
@Data
public class ArticleQueryParam {

    /**
     * 搜索词
     */
    private String searchWord;

    /**
     * 排序条件
     */
    private String sortCondition;
}
