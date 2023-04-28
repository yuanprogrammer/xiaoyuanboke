package com.xiaoyuan.common.util;

import com.xiaoyuan.common.enums.BaseCodeEnum;

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
}
