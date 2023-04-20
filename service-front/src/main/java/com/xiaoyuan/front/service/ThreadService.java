package com.xiaoyuan.front.service;

import com.xiaoyuan.front.mapper.ArticleMapper;
import com.xiaoyuan.front.mapper.FrontLogMapper;
import com.xiaoyuan.front.mapper.UserOperationMapper;
import com.xiaoyuan.model.entity.FrontLog;
import com.xiaoyuan.model.entity.UserOperation;
import com.xiaoyuan.model.param.mail.SendMailParam;
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
    private UserOperationMapper userOperationMapper;

    @Autowired
    private FrontLogMapper frontLogMapper;

    @Autowired
    private MailService mailService;

    // 文章浏览量 + 1
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, long id) {
        int result = articleMapper.updateViewCount(id);
        if (result == 0) {
            System.out.println("Error: 异常。。");
        }
    }

    /**
     * 发送邮箱
     * @param to 收件人
     * @param theme 主题
     * @param content 内容
     */
    @Async("taskExecutor")
    public void sendSimpleMail(String to, String theme, String content) {
        // openFeign
//        SendMailParam sendMailParam = new SendMailParam();
//        sendMailParam.setTo(to);
//        sendMailParam.setTheme(theme);
//        sendMailParam.setContent(content);

        try {
            mailService.sendSampleMail(to, theme, content);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Async("taskExecutor")
    public void recordUserOperation(Long number, String msg) {
        UserOperation userOperation = new UserOperation();
        userOperation.setUserId(number);
        userOperation.setDescribe(msg);
        userOperationMapper.insert(userOperation);
    }

    @Async("taskExecutor")
    public void recordBlogLog(FrontLog frontLog) {
        frontLogMapper.insert(frontLog);
    }
}
