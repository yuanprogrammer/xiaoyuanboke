package com.xiaoyuan.common.exception;

import com.xiaoyuan.common.enums.HttpStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FileName:    CustomerException
 * Author:      小袁
 * Date:        2022/5/9 18:06
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerException extends RuntimeException{

    private int code = 66666;
    private String msg;

    private CustomerException() {}

    public CustomerException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomerException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomerException(HttpStatusEnum httpStatusEnum) {
        super(httpStatusEnum.getName());
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getName();
    }

    public CustomerException(HttpStatusEnum httpStatusEnum, Throwable e) {
        super(httpStatusEnum.getName(), e);
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getName();
    }
}
