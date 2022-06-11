package com.xiaoyuan.utils;

import org.jsoup.Jsoup;

import java.util.Random;
import java.util.UUID;

/**
 * FileName:    getTextFromHTML
 * Author:      小袁
 * Date:        2022/4/16 17:46
 * Description:
 */
public class TextOperation {

    public static String getTextFromHtml(String html) {
        return Jsoup.parse(html).text();
    }

    public static String getArticleDigestFromText(String text) {
        if (text.length() <= 128) {
            return text;
        }else {
            return text.substring(0, 128);
        }
    }
}
