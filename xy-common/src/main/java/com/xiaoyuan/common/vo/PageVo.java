package com.xiaoyuan.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long total;

    private T list;

    public PageVo(T list, long total) {
        this.list = list;
        this.total = total;
    }
}
