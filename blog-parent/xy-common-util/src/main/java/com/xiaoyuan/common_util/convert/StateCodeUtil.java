package com.xiaoyuan.common_util.convert;

import com.xiaoyuan.common_util.base.BaseCodeEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class StateCodeUtil {

    private StateCodeUtil() {
    }

    public static <T> String toStateName(Integer code, Class<T> enumClass) {
        if (code == null || enumClass == null) return null;

        for (BaseCodeEnum baseCodeEnum : getBaseEnumList(enumClass)) {
            if (baseCodeEnum.getCode().equals(code)) {
                return baseCodeEnum.getName();
            }
        }

        return null;
    }

    public static <T> Integer toStateCode(String name, Class<T> enumClass) {
        if (name == null || enumClass == null) return null;

        for (BaseCodeEnum baseCodeEnum : getBaseEnumList(enumClass)) {
            if (baseCodeEnum.getName().equals(name)) {
                return baseCodeEnum.getCode();
            }
        }

        return null;
    }

    public static <T> BaseCodeEnum[] getBaseEnumList(Class<T> enumClass) {
        BaseCodeEnum[] baseCodeEnums;

        try {
            Method values = enumClass.getMethod("values");

            baseCodeEnums = (BaseCodeEnum[]) values.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            baseCodeEnums = new BaseCodeEnum[0];
        }

        return baseCodeEnums;
    }

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 100; i += 10) {
            int s = i;
            new Thread(() -> {
                for (int j = s; j <= s + 9; j++) {
                    count += j;
                }
            }).start();
        }

        Thread.sleep(1500);

        System.out.println("结果" + count);
    }
}
