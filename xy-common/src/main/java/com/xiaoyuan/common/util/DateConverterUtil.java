package com.xiaoyuan.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName:    DateOperation
 * Author:      小袁
 * Date:        2022/4/20 18:33
 * Description: 日期相关操作
 */
public class DateConverterUtil {

    /**
     * 由 String格式日期 转换为 Date格式日期
     * @param date 原日期
     * @return 转换后的日期
     * @throws ParseException
     */
    public static Date toDateFromString(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date resultDate = new Date();
        try {
            resultDate =  format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    public static Date toDateFromString(String date, boolean isWhole) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = new Date();
        try {
            resultDate =  format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }


    /**
     * 由 Date格式日期 转换为 String格式日期
     * @param date 原日期
     * @return 转换后的日期
     */
    public static String toStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String toStringFromDate(Date date, boolean isWhole) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
