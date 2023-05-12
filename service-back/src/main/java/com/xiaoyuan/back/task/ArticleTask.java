package com.xiaoyuan.back.task;

import cn.hutool.json.JSONUtil;
import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.common.param.ArticleParam;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @Author: YDW
 * @Date: 2023-05-12 09:54
 */
@Slf4j
public class ArticleTask extends TimerTask {

    private final ArticleService articleService;

    private final ArticleParam articleParam;

    private final Long authorId;

    public ArticleTask(ArticleService articleService, ArticleParam articleParam, Long authorId) {
        this.articleService = articleService;
        this.articleParam = articleParam;
        this.authorId = authorId;
    }

    @Override
    public void run() {
        log.info("开始发布文章...");
        log.info("参数：{}", JSONUtil.toJsonStr(articleParam));
        int res = articleService.schedulePublish(articleParam, authorId);
        if (res != 1) {
            log.error("文章定时发布失败");
        }
    }
}
