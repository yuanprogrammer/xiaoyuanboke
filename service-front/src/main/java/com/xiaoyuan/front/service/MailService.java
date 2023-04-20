package com.xiaoyuan.front.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    /**
     * 发送简单的邮箱
     *
     * @param to 收件人
     * @param theme 标题
     * @param content 正文内容
     * @param cc 抄送
     */
    void sendSampleMail(String to, String theme, String content, String... cc) throws UnsupportedEncodingException;

    /**
     * 发送HTML邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;
}
