package com.xiaoyuan.utils.constant;

import lombok.Getter;

/**
 * FileName:    Code
 * Author:      小袁
 * Date:        2022/5/1 23:29
 * Description:
 */
@Getter
public enum HttpStatusEnum {


    INTERNAL_SERVER_ERROR(500, "服务器异常"),

    USER_NOT_EXIST(40001, "账号不存在"),
    USER_ALREADY_EXIST(40002, "用户已存在"),
    PASSWORD_ERROR(40003, "密码错误"),
    PASSWORD_INCONSISTENT(40004, "密码不一致"),
    TOKEN_INVALID(40005, "登录信息已过期, 请重新登陆"),
    USER_NO_LOGIN(40006, "请先登录"),
    EMAIL_ALREADY_EXIST(40007, "邮箱已被注册"),
    MOBILE_NUMBER_ALREADY_EXIST(40008, "号码已被注册"),
    USER_OPERATION_ERROR(40009, "操作失败"),
    PARAM_ERROR(410001, "参数错误"),
    PARAM_LENGTH_BEYOND(410002, "参数超出长度"),
    EMAIL_ERROR(41003, "邮箱格式错误"),
    MOBILE_NUMBER_ERROR(41004, "手机号码格式错误"),
    CODE_ERROR(41005, "验证码不正确"),
    CODE_INVALID(41006, "验证码失效, 请重新发送"),
    PARAM_ILLEGAL(41007, "参数不合法"),
    NO_SUPPORT_EMOJI(41008, "暂不支持表情"),
    CODE_SEND_ERROR(41009, "验证码发送失败"),

    UNKNOWN_ERROR(66666, "未知异常, 联系管理员"),
    ILLEGAL_OPERATION(88888, "非法操作");

    private final int code;
    private final String msg;

    private HttpStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
