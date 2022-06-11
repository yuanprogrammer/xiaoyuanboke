package com.xiaoyuan.front.vo;

import lombok.Data;

/**
 * FileName:    TimeLineVo
 * Author:      小袁
 * Date:        2022/4/23 23:36
 * Description: 时间线Vo
 */
@Data
public class TimeLineVo {

    private String title;

    private String describe;

    private String content;

    private String startTime;

    private String endTime;
}
