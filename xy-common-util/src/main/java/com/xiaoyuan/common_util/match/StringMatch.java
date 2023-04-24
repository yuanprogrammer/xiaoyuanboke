package com.xiaoyuan.common_util.match;

import com.xiaoyuan.common_util.auth.EncryptionAlgorithmUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName:    StringMatch
 * Author:      小袁教程
 * Date:        2022/8/3 21:55
 * Description:
 */
public final class StringMatch {

    private StringMatch() { }

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
     * 是否为正确的邮箱
     *
     * @param email 待检验邮箱字符串
     * @return true or false
     */
    public static boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return emailPattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        System.out.println(isEmail("1971788445@qq.com"));
    }

    /**
     * 是否为正确的手机号码
     *
     * @param phone 待检验手机号码字符串
     * @return true or false
     */
    public static boolean isPhone(String phone) {
        Pattern phonePattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        return phonePattern.matcher(phone).matches();
    }

    /**
     * 判断字符串里面是否包含中文, 不包括中文符号
     *
     * @param str 待检验字符串
     * @return 包含->true
     */
    public static boolean isContainsChinese(String str) {
        Pattern strPattern = Pattern.compile("[\u4e00-\u9fa5]");
        return strPattern.matcher(str).find();
    }

    /**
     * 判断字符串中是否含有表情符号
     *
     * @param str 待检验字符串
     * @return 包含->true
     */
    public static boolean isContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char hs = str.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (str.length() > 1) {
                    char ls = str.charAt(i + 1);
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
                if (str.length() > 1 && i < str.length() - 1) {
                    char ls = str.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断某个字符是不是表情
     *
     * @param codePoint 待检验字符
     * @return true or false
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
