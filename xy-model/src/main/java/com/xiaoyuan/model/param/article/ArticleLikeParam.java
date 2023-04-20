package com.xiaoyuan.model.param.article;

import com.xiaoyuan.model.common.Pagination;
import com.xiaoyuan.model.constants.valid.Number;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleLikeParam extends Pagination {

    @NotNull
    @Number
    private String number;
}
