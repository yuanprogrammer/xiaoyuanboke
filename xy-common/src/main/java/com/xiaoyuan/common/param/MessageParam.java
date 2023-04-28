package com.xiaoyuan.common.param;

import com.xiaoyuan.common.vo.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageParam extends BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    // 消息唯一ID
    private String id;

    // 状态
    private String status;

    // 消息类型
    private String type;

    // 消息发送时间，13位毫秒
    private Long sendTime;

    // 消息内容，如果type=file，此属性表示文件的URL地址
    private String content;

    // 	文件大小
    private Integer fileSize;

    // 文件名称
    private String fileName;

    // 接收消息的联系人ID
    private String toContactId;

    // 消息发送人的信息
    private MessageSenderParam fromUser;
}
