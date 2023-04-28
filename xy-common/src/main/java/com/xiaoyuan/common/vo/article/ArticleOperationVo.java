package com.xiaoyuan.common.vo.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * FileName:    ArticleLikeVo
 * Author:      小袁教程
 * Date:        2022/5/14 12:09
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleOperationVo {

    // 点赞数量
    private Integer likeCount;

    // 收藏数量
    private Integer collectCount;

    // 状态
    private Boolean status;
}
