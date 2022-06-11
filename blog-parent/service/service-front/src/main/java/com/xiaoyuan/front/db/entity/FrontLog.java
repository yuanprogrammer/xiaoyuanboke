package com.xiaoyuan.front.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * FileName:    FrontLog
 * Author:      小袁教程
 * Date:        2022/5/17 16:30
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FrontLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // ip
    private String ip;
    // url
    private String url;
    // http方法 GET POST PUT DELETE PATCH
    @TableField(value = "`httpMethod`")
    private String httpMethod;
    // 类方法
    @TableField(value = "`classMethod`")
    private String classMethod;
    // 接口耗时
    @TableField(value = "`timeCost`")
    private Long timeCost;
    // 操作系统
    private String os;
    // 浏览器
    private String browser;
    // user-agent
    @TableField(value = "`userAgent`")
    private String userAgent;
    // 时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
