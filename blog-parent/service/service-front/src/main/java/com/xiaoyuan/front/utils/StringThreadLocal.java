package com.xiaoyuan.front.utils;

import com.xiaoyuan.front.vo.CommonUserVo;

/**
 * FileName:    StringThreadLocal
 * Author:      小袁
 * Date:        2022/5/3 15:10
 * Description:
 */
public class StringThreadLocal {

    private StringThreadLocal() {}

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void put(String s) {
        LOCAL.set(s);
    }

    public static String get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
