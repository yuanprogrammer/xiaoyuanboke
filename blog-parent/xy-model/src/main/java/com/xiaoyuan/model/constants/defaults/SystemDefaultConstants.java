package com.xiaoyuan.model.constants.defaults;

import com.xiaoyuan.common_util.generate.RandomUtil;

public interface SystemDefaultConstants {

    String SYS_USER_AVATAR = "https://user.xiaoyuan-boke.com/user/avatar/user_default.png";
    String SYS_USER_CODE = "sysUser:" + RandomUtil.randomStrUUID(true);
    String SYS_USER_ROLE_ID = "[6]";
    String SYS_USER_NICKNAME = "笔者";
    Integer SYS_USER_STATE = 0;
}
