package com.xiaoyuan.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:    TimeLineVo
 * Author:      小袁
 * Date:        2022/4/20 12:34
 * Description:
 */
@Data
public class TimeLineVo implements Serializable {

    private final static long serialVersionUID = 1L;

    private String id;

    private String title;

    private String describe;

    private String content;

    private String startTime;

    private String endTime;
}
