package com.xiaoyuan.common_util.generate;

import java.util.Random;
import java.util.UUID;

public final class RandomUtil {

    private RandomUtil() {}

    /**
     * 随机生成六位数字验证码
     */
    public static String randomSixCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    public static String randomStrUUID(boolean isWhole) {
        return isWhole ? UUID.randomUUID().toString() : UUID.randomUUID().toString().replaceAll("-", "");
    }
}
