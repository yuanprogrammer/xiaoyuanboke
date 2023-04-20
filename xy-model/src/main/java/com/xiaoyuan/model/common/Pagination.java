package com.xiaoyuan.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageIndex = 1;

    private int pageSize = 10;
}
