package com.xiaoyuan.model.param.problem;

import com.xiaoyuan.model.common.Pagination;
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
