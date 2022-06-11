package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.ArticleCollect;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    ArticleCollectService
 * Author:      小袁
 * Date:        2022/5/11 9:28
 * Description:
 */
public interface ArticleCollectService extends IService<ArticleCollect> {

    /**
     * 收藏
     * @param number
     * @return
     */
    R collectArticle(String number);

    R getCollectInfo(String token, String number);
}
