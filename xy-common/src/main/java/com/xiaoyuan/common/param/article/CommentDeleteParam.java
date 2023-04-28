package com.xiaoyuan.common.param.article;

import com.xiaoyuan.common.constants.valid.Number;
import lombok.Data;

@Data
public class CommentDeleteParam {

    @Number
    private String number;
}
