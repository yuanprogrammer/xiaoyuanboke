package com.xiaoyuan.model.param.mail;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SendMailCodeParam {

    @NotNull
    @Email
    private String email;
}
