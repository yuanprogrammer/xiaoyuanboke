package com.xiaoyuan.back.job;

import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.model.param.ArticleParam;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 定时发布文章 调度器
 */
@Slf4j
@Component
public class ArticleJob extends QuartzJobBean {

    @Autowired
    private ArticleService articleService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取 文章对象
        ArticleParam articleParam = (ArticleParam) jobExecutionContext.getJobDetail().getJobDataMap().get("articleParam");

        // 发布文章
        articleService.insert(articleParam);

        log.info("定时发布成功");
    }
}
