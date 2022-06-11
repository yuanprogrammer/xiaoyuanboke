package com.xiaoyuan.back;

import com.xiaoyuan.back.db.dao.ArticleMapper;
import com.xiaoyuan.back.db.dao.CommonUserMapper;
import com.xiaoyuan.back.db.entity.Article;
import com.xiaoyuan.back.db.entity.CommonUser;
import com.xiaoyuan.back.service.CategoryService;
import com.xiaoyuan.utils.vo.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * FileName:    Test
 * Author:      小袁
 * Date:        2022/4/15 14:31
 * Description:
 */
@SpringBootTest
public class BackApplicationTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommonUserMapper commonUserMapper;

    @Test
    public void test1() {
        CommonUser commonUser = new CommonUser();
        commonUser.setUsername("xiaoyuan");
        commonUser.setNickname("小袁同学");
        commonUserMapper.insert(commonUser);
    }

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test2() {
        Article article = new Article();
        article.setId(1515281126715850753L);
        article.setViewCount(10);
        articleMapper.updateById(article);
    }
}
