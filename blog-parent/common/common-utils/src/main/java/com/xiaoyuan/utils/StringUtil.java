package com.xiaoyuan.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName:    StringUtil
 * Author:      小袁
 * Date:        2022/4/25 15:15
 * Description: 字符串相关操作工具类
 */
public class StringUtil {

    /**
     * 邮箱校验
     *
     * @param email 邮箱
     * @return true or false
     */
    public static boolean checkEmail(String email) {
        String check = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    /**
     * 手机号码校验
     *
     * @param phone
     * @return true or false
     */
    public static boolean checkPhone(String phone) {
        Matcher m = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(phone);
        return m.matches();
    }

    /**
     * 用户名校验
     * 长度 5-20 个字符，只能包含数字、大小写字母 且 至少包含一个字母
     *
     * @param username 用户名
     * @return true or false
     */
    public static boolean checkUsername(String username) {
        Matcher m = Pattern.compile("(?=.*[a-zA-Z])[a-zA-Z0-9]{5,20}").matcher(username);
        return m.matches();
    }

    /**
     * 编号校验
     * @param number
     * @return
     */
    public static boolean checkNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }else {
            number = EncryptionAlgorithmUtil.decode(number);
            if (StringUtils.isBlank(number)) {
                return false;
            }else {
                return number.length() == 19;
            }
        }
    }

    /**
     * 随机生成六位数字验证码
     */
    public static String randomSixCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    /**
     * 检验字符串中是否包含中文，不包括中文字符
     *
     * @param str 待验证字符串
     * @return true-包含  false-不包含
     */
    public static boolean isContainsChinese(String str) {
        Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(str);
        return m.find();
    }

    /**
     * 判断字符串中是否含有表情
     *
     * @param source
     * @return
     */
    public static boolean isContainsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    /**
     * 判断某个字符是不是表情
     *
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤掉字符串中的表情
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }
}
