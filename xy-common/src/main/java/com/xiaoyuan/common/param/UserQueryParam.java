package com.xiaoyuan.common.param;

import com.xiaoyuan.common.vo.Pagination;
import com.xiaoyuan.common.constants.MatchConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * FileName:    UserQueryParam
 * Author:      小袁教程
 * Date:        2022/6/7 0:24
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryParam extends Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String nickname;

    @NotNull
    @Pattern(regexp = MatchConstants.email, message = "邮箱格式不正确")
    private String email;

    private String mobileNumber;

    private String gmtCreate;
}
