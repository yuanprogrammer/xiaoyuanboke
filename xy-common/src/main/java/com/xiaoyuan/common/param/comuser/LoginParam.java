package com.xiaoyuan.common.param.comuser;

import com.xiaoyuan.common.constants.valid.NotChinese;
import com.xiaoyuan.common.constants.valid.NotEmoji;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * FileName:    LoginParam
 * Author:      小袁
 * Date:        2022/5/1 22:53
 * Description:
 */
@Data
public class LoginParam {

    @NotNull
    @NotEmoji
    @NotChinese
    @Length(max = 25)
    private String account;

    @NotNull
    @NotEmoji
    @NotChinese
    @Length(max = 20)
    private String password;
}
