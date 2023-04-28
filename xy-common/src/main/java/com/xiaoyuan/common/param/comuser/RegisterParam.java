package com.xiaoyuan.common.param.comuser;

import com.xiaoyuan.common.constants.valid.NotChinese;
import com.xiaoyuan.common.constants.valid.NotEmoji;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class RegisterParam {

    @NotNull
    //@NotChinese
    //@NotEmoji
    private String account;

    @NotNull
    @Length(min = 6, max = 20)
    //@NotChinese
    //@NotEmoji
    private String password;

    @NotNull
    //@NotChinese
    //@NotEmoji
    private String passwordConfirm;

    /**
     * 1 -> 用户名登陆
     * 2 -> 邮箱登陆
     * 3 -> 手机号码登陆
     */
    @NotNull
    private String select;

    // 验证码
    @NotNull
    private String code;
}
