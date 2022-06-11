package com.xiaoyuan.back.vo;

import lombok.Data;

import java.util.Date;

/**
 * FileName:    TimeLineVo
 * Author:      小袁
 * Date:        2022/4/20 12:34
 * Description:
 */
@Data
public class TimeLineVo {

    private String id;

    private String title;

    private String describe;

    private String content;

    private String startTime;

    private String endTime;
}
