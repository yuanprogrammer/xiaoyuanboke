package com.xiaoyuan.back.vo.param;

import lombok.Data;

/**
 * FileName:    CategoryParam
 * Author:      小袁
 * Date:        2022/5/7 10:56
 * Description:
 */
@Data
public class CategoryParam {

    private String oneOrTwo;

    private String name;

    private Integer parentId;
}
