package com.xiaoyuan.common.param.problem;

import com.xiaoyuan.common.vo.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProblemQueryParam extends Pagination {

    private String id;

    private String email;

    private Integer problemState;

    private Integer noticeState;

    private String startTime;

    private String endTime;
}
