package com.xiaoyuan.common.param;

import com.xiaoyuan.common.constants.MatchConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SendSmsCodeParam {

    @NotNull
    @Pattern(regexp = MatchConstants.phone)
    private String phone;
}
