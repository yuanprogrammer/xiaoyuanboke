package com.xiaoyuan.model.vo;

import com.xiaoyuan.model.enums.HttpStatusEnum;
import lombok.Data;

/**
 * FileName:    R
 * Author:      小袁
 * Date:        2022/3/12 12:23
 * Description: 统一结果返回的类
 */
@Data
public class R<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    // 把构造方法私有化
//    private R() {}

    // 成功静态方法
    public static <T> R<T> success() {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(HttpStatusEnum.SUCCESS.getName());
        return r;
    }

    public static <T> R<T> success(String message) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.message(message);
        return r;
    }

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.setData(object);
        r.setSuccess(true);
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(HttpStatusEnum.SUCCESS.getName());
        return r;
    }

    public static <T> R<T> success(String msg, T object) {
        R<T> r = new R<>();
        r.setData(object);
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(msg);
        return r;
    }

    // 失败静态方法
    public static <T> R<T> fail() {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setCode(HttpStatusEnum.FAIL.getCode());
        r.setMessage(HttpStatusEnum.FAIL.getName());
        return r;
    }

    public static <T> R<T> fail(String msg) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setCode(HttpStatusEnum.FAIL.getCode());
        r.setMessage(msg);
        return r;
    }

    public static <T> R<T> fail(HttpStatusEnum httpStatusEnum) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setCode(httpStatusEnum.getCode());
        r.setMessage(httpStatusEnum.getName());
        return r;
    }

    public R<T> message(String message){
        this.setMessage(message);
        return this;
    }

    public R<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public R<T> data(T data){
        this.setData(data);
        return this;
    }
}
