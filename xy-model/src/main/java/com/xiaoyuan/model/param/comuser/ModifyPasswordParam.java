package com.xiaoyuan.model.param.comuser;

import com.xiaoyuan.model.constants.valid.NotChinese;
import com.xiaoyuan.model.constants.valid.NotEmoji;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class ModifyPasswordParam {

    @NotNull
    //@NotEmoji
    //@NotChinese
    @Length(min = 6, max = 20)
    private String password;

    @NotNull
    //@NotEmoji
    //@NotChinese
    @Length(min = 6, max = 20)
    private String passwordConfirm;
}
