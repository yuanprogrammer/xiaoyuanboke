package com.xiaoyuan.back.service;

import com.xiaoyuan.common.param.ArticleParam;
import com.xiaoyuan.common.vo.R;

public interface SchedulerService {

    R publishArticle(ArticleParam articleParam);
}
