package com.xiaoyuan.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName:    FriendLink
 * Author:      小袁
 * Date:        2022/4/30 15:01
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "xy_friend_link")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 领域
     */
    private String field;

    /**
     * 简单描述
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 链接
     */
    private String link;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
