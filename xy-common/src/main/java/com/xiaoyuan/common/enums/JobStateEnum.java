package com.xiaoyuan.common.enums;

public enum JobStateEnum implements BaseCodeEnum {

    READY(1, "等待执行"),
    RUN(2, "执行中"),
    FINISH(3, "已结束"),
    INTERRUPT(4, "中断"),
    STOP(5, "停止"),
    EXCEPTIONAL(6, "异常")
    ;

    private Integer code;

    private String name;

    JobStateEnum(Integer code, String name) {
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
