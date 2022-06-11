package com.xiaoyuan.back.vo;

import lombok.Data;

import java.util.Date;

/**
 * FileName:    HomeMessageVo
 * Author:      小袁
 * Date:        2022/5/1 8:39
 * Description:
 */
@Data
public class HomeMessageVo {

    private String id;

    /**
     * 留言人编号
     */
    private String authorId;

    /**
     * 简述留言（正面）
     */
    private String content;

    /**
     * 详细留言（背面, 可选）
     */
    private String detailContent;

    /**
     * 背景图（随机）
     */
    private String background;

    /**
     * 留言时间
     */
    private Date gmtCreate;
}
