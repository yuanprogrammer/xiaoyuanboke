package com.xiaoyuan.front.utils;

import com.xiaoyuan.front.vo.CommonUserVo;

/**
 * FileName:    UserThreadLocal
 * Author:      小袁
 * Date:        2022/5/3 9:26
 * Description:
 */
public class UserThreadLocal {

    // 构造私有化
    private UserThreadLocal() {}

    private static final ThreadLocal<CommonUserVo> LOCAL = new ThreadLocal<>();

    public static void put(CommonUserVo commonUserVo) {
        LOCAL.set(commonUserVo);
    }

    public static CommonUserVo get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
