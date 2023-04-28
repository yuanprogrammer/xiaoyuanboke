package com.xiaoyuan.common.param.comuser;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class FindPasswordParam {

    @NotNull
    private String account;

    @NotNull
    @Length(min = 6, max = 20)
    private String password;

    // 验证码
    @NotNull
    @Length(min = 6, max = 6)
    private String code;
}
