package com.xiaoyuan.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  对外暴露Id异或加密解密
 *
 * @author lwj
 * @date 2022-01-13 16:42:11
 */
public class EncryptionAlgorithmUtil {

    public static String encrypt(String key) {
        // 对 10 - length 长度的数字进行转换
        Integer src = Integer.parseInt(key.substring(0, 10));

        String timeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long pwd = Long.parseLong(src + timeStr);
        Long xorPwd = pwd ^ Integer.parseInt(timeStr);

        // 回传转换后的字符串
        return Long.toHexString(xorPwd) + key.substring(10) + (key.length() - 10);
    }

    public static String decode(String key) {
        try {
            int start = key.length() - Integer.parseInt(key.substring(key.length() - 1));
            // 截取 10 - length 长度进行解码
            key = key.substring(0, key.length() - 1);
            String src = key.substring(0, start - 1);

            Long timeInt = Long.parseLong(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

            Long xorOrigin = (Long.parseLong(src, 16)) ^ timeInt;
            Long id = (xorOrigin - timeInt) / 100000000;

            // 回传解码的字符串
            if (id * 100000000 == xorOrigin - timeInt) {
                return id.intValue() + key.substring(start - 1);
            }
        }catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            return "";
        }
        return "";
    }

    public static void main(String[] args) {
        String key = "1531261963024805889";
        String encKey = encrypt(key);
//        System.out.println(encrypt("1526785944126025730"));
        System.out.println(encKey + "\t" + encKey.length());
        System.out.println(key);
        System.out.println(decode(encKey));
    }
}
