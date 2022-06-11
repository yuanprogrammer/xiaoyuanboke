package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.ArticleCategoryMapper;
import com.xiaoyuan.back.db.entity.ArticleCategory;
import com.xiaoyuan.back.service.ArticleCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:    ArticleCategoryServiceImpl
 * Author:      小袁
 * Date:        2022/4/16 18:11
 * Description:
 */
@Service
@Transactional
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

}
