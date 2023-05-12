package com.xiaoyuan.back.task;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.common.param.ArticleParam;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务处理器
 *
 * @Author: YDW
 * @Date: 2023-05-12 09:52
 */
@Component
public class TaskProcessor {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public void startArticleTask(String time, ArticleParam articleParam) {
        long delay = DateUtil.between(DateUtil.parse(time), new Date(), DateUnit.MINUTE);
        executorService.schedule(new ArticleTask(SpringUtil.getBean(ArticleService.class), articleParam, StpUtil.getLoginIdAsLong()), delay, TimeUnit.MILLISECONDS);
    }
}
