package com.xiaoyuan.front.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * FileName:    CommonUser
 * Author:      小袁
 * Date:        2022/4/17 16:35
 * Description: 普通用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommonUser {

    /**
     * 用户编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户用户名
     */
    private String username;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号码
     */
    private String mobileNumber;

    /**
     * 用户微信号
     */
    private String wechatNumber;

    /**
     * 用户头像地址
     */
    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 注册时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 登陆时间
     */
    private Date gmtLogin;
}
