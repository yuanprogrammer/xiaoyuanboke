package com.xiaoyuan.model.param.article;

import com.xiaoyuan.model.constants.valid.Number;
import lombok.Data;

@Data
public class CommentDeleteParam {

    @Number
    private String number;
}
