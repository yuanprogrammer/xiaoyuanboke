package com.xiaoyuan.model.param;

import com.xiaoyuan.model.vo.PageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * FileName:    UsesrOperationParam
 * Author:      小袁教程
 * Date:        2022/6/7 12:14
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserOperationParam extends PageUtils implements Serializable {

    private String nickname;

    private String username;

    private String email;

    private String mobileNumber;

    private String describe;

    private String operatedTime;
}
