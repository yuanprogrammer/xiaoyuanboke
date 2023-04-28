package com.xiaoyuan.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_notice")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "sender_code")
    @ApiModelProperty(value = "发送方编号")
    private String senderCode;

    @TableField(value = "receiver_code")
    @ApiModelProperty(value = "接收方编号")
    private String receiverCode;

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    @TableField(value = "read_time")
    private Date readTime;

    @TableField(value = "notice_time")
    private Date noticeTime;

    @TableField(value = "notice_type")
    private Character noticeType;

    @TableField(value = "state")
    private Character state;
}
