package com.xiaoyuan.common.util;

public class ConverterUtil {

    public static String viewNumberFormat(Integer number) {
        if (number < 10000) {
            return number.toString();
        }else if (number <= 100000) {
            return number / 1000 % 10 == 0 ? number / 10000 + "万+" : number / 10000 + "." + number / 1000 % 10 + "万+";
        }else {
            return number / 10000 + "万+";
        }
    }
}
