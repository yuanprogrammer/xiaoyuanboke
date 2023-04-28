package com.xiaoyuan.common.vo;

import cn.hutool.core.date.DateUtil;
import com.xiaoyuan.common.util.StateCodeUtil;
import com.xiaoyuan.common.util.RandomUtil;
import com.xiaoyuan.common.enums.MessageEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 初始化操作
     */
    public BaseMessage() {
        // 初始化编号
        this.msgCode = "msg::" + RandomUtil.randomStrUUID(true);
        // 初始化状态
        this.msgState = 0;
        // 初始化时间
        this.msgSendTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 消息编号
     */
    private String msgCode;

    /**
     * 消息类型（1->通知, 2->单人聊天)
     */
    private Integer msgType;

    /**
     * 消息类型转换
     */
    private String msgTypeTrans;

    /**
     * 消息发送者编号
     */
    private String senderCode;

    /**
     * 消息接收者编号
     */
    private String receiverCode;

    /**
     * 消息状态
     */
    private Integer msgState;

    /**
     * 消息状态转换
     */
    private String msgStateTrans;

    /**
     * 消息发送时间
     */
    private String msgSendTime;

    /**
     * 消息接收时间
     */
    private String msgReceiveTime;

    /**
     * 转化工具
     */
    public void transMessage() {
        if (this.msgType != null) {
            this.msgTypeTrans = StateCodeUtil.toStateName(this.msgType, MessageEnum.class);
        }
        if (this.msgState != null) {
            this.msgStateTrans = StateCodeUtil.toStateName(this.msgState, MessageEnum.class);
        }
    }
}
