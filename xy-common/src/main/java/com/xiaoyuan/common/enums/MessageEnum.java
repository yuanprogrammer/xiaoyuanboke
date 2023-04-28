package com.xiaoyuan.common.enums;

public enum MessageEnum implements BaseCodeEnum {

    COLLECTION_NOTICE(1, "收藏通知"),
    LIKE_NOTICE(2, "点赞通知"),
    COMMENT_NOTICE(3, "评论通知"),
    REPLY_NOTICE(4, "回复通知"),
    ONE_CHAT(5, "单人聊天"),
    MORE_CHAT(6, "群发聊天"),
    PULL_UNREAD_MESSAGE(7, "拉取离线消息"),

    TO_BE_SEND(100, "待发送"),
    TO_BE_RECEIVE(101, "待接收"),
    SENDING(102, "发送中"),
    ALREADY_RECEIVE_CONFIRM(103, "已接收确认"),
    ALREADY_RECEIVE_NOT_CONFIRM(104, "接收未确认")
    ;

    private Integer code;

    private String name;

    MessageEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
