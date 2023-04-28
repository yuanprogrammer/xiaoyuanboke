package com.xiaoyuan.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticeVo extends BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private String readTime;

    private String noticeTime;

    private Integer noticeType;
}
