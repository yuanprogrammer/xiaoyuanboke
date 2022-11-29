package com.xiaoyuan.back.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.xiaoyuan.back.job.ArticleJob;
import com.xiaoyuan.back.service.SchedulerService;
import com.xiaoyuan.common_util.convert.DateConverterUtil;
import com.xiaoyuan.model.param.ArticleParam;
import com.xiaoyuan.model.vo.R;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public R publishArticle(ArticleParam articleParam) {
        try {
            // 获取调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 封装数据
            articleParam.setUserId(StpUtil.getLoginIdAsString());
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("articleParam", articleParam);
            // 创建发布任务
            JobDetail publishJob = JobBuilder.newJob(ArticleJob.class)
                    .withDescription("定时发布文章")
                    .withIdentity(UUID.randomUUID().toString(true))
                    .usingJobData(jobDataMap)
                    .build();
            // 创建触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withDescription("定时发布文章触发器")
                    .withIdentity(UUID.randomUUID().toString(true))
                    .startAt(new Date(articleParam.getPublishTime()))
                    .build();

            // 绑定到调度器
            scheduler.scheduleJob(publishJob, trigger);
            // 启动
            scheduler.start();

            return R.success();
        }catch (SchedulerException e) {
            e.printStackTrace();
            return R.fail("发布失败");
        }
    }
}
