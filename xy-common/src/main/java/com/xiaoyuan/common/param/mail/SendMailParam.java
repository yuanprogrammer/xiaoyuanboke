package com.xiaoyuan.common.param.mail;

import lombok.Data;

import java.util.List;

@Data
public class SendMailParam {

    private String to;

    private List<String> tos;

    private String theme;

    private String content;

    private String[] cc;
}
