package com.xiaoyuan.back.service;

import com.xiaoyuan.model.param.ArticleParam;
import com.xiaoyuan.model.vo.R;

public interface SchedulerService {

    R publishArticle(ArticleParam articleParam);
}
