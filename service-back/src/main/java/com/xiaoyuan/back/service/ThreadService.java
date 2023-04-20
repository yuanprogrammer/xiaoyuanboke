package com.xiaoyuan.back.service;

import com.xiaoyuan.model.vo.ProblemFeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * FileName:    ThreadService
 * Author:      小袁
 * Date:        2022/4/22 16:09
 * Description: 线程服务
 */
@Component
public class ThreadService {

    @Autowired
    private MailService mailService;

    @Async("taskExecutor")
    public void sendProblemSolveNoticeToUser(ProblemFeedbackVo problemFeedbackVo) {
        // 设置邮件内容
        String content = "编号：" + problemFeedbackVo.getId() + "\n"
                + "\n" + "您所反馈的问题：" + problemFeedbackVo.getProblem() + "\n"
                + "\n" + "感谢您的反馈 ❤";
        // 发送邮件通知
//        mailService.sendSimpleMail(problemFeedbackVo.getEmail(), "您反馈的问题已经解决", content);
    }

    /**
     * 发送邮箱
     * @param to 收件人
     * @param theme 主题
     * @param content 内容
     */
    @Async("taskExecutor")
    public void sendSimpleMail(String to, String theme, String content) {
        try {
            mailService.sendSampleMail(to, theme, content);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
