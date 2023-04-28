package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common.util.EncryptionAlgorithmUtil;
import com.xiaoyuan.common.util.DateConverterUtil;
import com.xiaoyuan.common.util.NumberConverterUtil;
import com.xiaoyuan.common.util.StringMatch;
import com.xiaoyuan.front.mapper.ArticleContentMapper;
import com.xiaoyuan.front.mapper.ArticleMapper;
import com.xiaoyuan.front.mapper.CategoryMapper;
import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.ThreadService;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.common.vo.PageVo;
import com.xiaoyuan.common.pojo.Article;
import com.xiaoyuan.common.enums.HttpStatusEnum;
import com.xiaoyuan.common.param.article.ArticleQueryParam;
import com.xiaoyuan.common.param.article.CategoryQueryParam;
import com.xiaoyuan.common.vo.R;
import com.xiaoyuan.common.vo.article.ArticleVo;
import com.xiaoyuan.common.vo.article.RecommendArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:    ArticleServiceImpl
 * Author:      小袁
 * Date:        2022/4/24 17:45
 * Description:
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final int HOST_NEW_LIMIT = 3;

    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Autowired
    private ThreadService threadService;

    @Override
    public List<RecommendArticleVo> findHostsArticle() {
        return encryptionNumber(this.baseMapper.findHosts(HOST_NEW_LIMIT));
    }

    @Override
    public List<RecommendArticleVo> findNewsArticle() {
        return encryptionNumber(this.baseMapper.findNews(HOST_NEW_LIMIT));
    }

    @Override
    public List<ArchivesVo> findArchives() {
        return this.baseMapper.findArchives();
    }

    @Override
    public Integer findTotal() {
        return this.baseMapper.findTotal();
    }

    @Override
    public String findViewCount() {
        return NumberConverterUtil.viewNumberFormat(this.baseMapper.findViewCount());
    }

    @Override
    public R listPageArticle(ArticleQueryParam queryParam) {
        IPage<ArticleVo> iPage = this.baseMapper.findArticleList(new Page<>(queryParam.getPageIndex(), queryParam.getPageSize()), queryParam);

        List<ArticleVo> articleVoList = iPage.getRecords();

        articleVoList.forEach(item -> {
            item.setNumber(EncryptionAlgorithmUtil.encrypt(item.getNumber()));
        });

        return R.success(new PageVo<>(articleVoList, iPage.getTotal()));
    }

    @Override
    public R listArchivesArticle() {
        List<ArchivesVo> archivesArticleList = this.baseMapper.findArchivesArticleList();
        for (int i = 0; i < archivesArticleList.size(); i++) {
            for (int j = 0; j < archivesArticleList.get(i).getArticleList().size(); j++) {
                String number = archivesArticleList.get(i).getArticleList().get(j).getNumber();
                archivesArticleList.get(i).getArticleList().get(j).setNumber(EncryptionAlgorithmUtil.encrypt(number));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("archives", archivesArticleList);

        return R.success(map);
    }

    @Override
    public R listCategoryArticleList(CategoryQueryParam categoryQueryParam) {
        int index = categoryQueryParam.getPageIndex();
        int size = categoryQueryParam.getPageSize();

        // 分页对象
        Page<Article> page = new Page<>(index, size);
        IPage<Article> iPage = this.baseMapper.findCategoryArticleList(page, categoryQueryParam.getCategoryId());
        // 转换
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : iPage.getRecords()) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setNumber(EncryptionAlgorithmUtil.encrypt(String.valueOf(article.getId())));
            articleVo.setTitle(article.getTitle());
            articleVo.setDigest(article.getDigest());
            articleVo.setPublishTime(DateConverterUtil.toStringFromDate(article.getGmtCreate(), true));
            articleVo.setViewCount(article.getViewCount());

            articleVoList.add(articleVo);
        }

        return R.success(articleVoList);
    }

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R getArticleDetailById(String number) {
        if (!StringMatch.checkNumber(number)) return R.fail(HttpStatusEnum.PARAM_ERROR);

        long id = Long.parseLong(EncryptionAlgorithmUtil.decode(number));
        ArticleVo articleVo = this.baseMapper.getArticleDetailById(id);

        // 浏览量 + 1
        threadService.updateArticleViewCount(this.baseMapper, id);

        // 分类
        articleVo.setCategoryList(categoryMapper.findArticleCategory(id));
        Map<String, Object> map = new HashMap<>();
        map.put("article", articleVo);

        return R.success(map);
    }

    @Override
    public R getArticleMarkdownById(String number) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", articleContentMapper.getArticleMarkdownById(EncryptionAlgorithmUtil.decode(number)));

        return R.success(map);
    }

    @Override
    public boolean isExistById(String number) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("id", EncryptionAlgorithmUtil.decode(number));
        wrapper.select("id");
        wrapper.last("limit 1");
        return this.baseMapper.selectOne(wrapper) != null;
    }

    // 对推荐文章的编号进行加密
    private List<RecommendArticleVo> encryptionNumber(List<RecommendArticleVo> recommendArticleVos) {
        recommendArticleVos.forEach(item -> {
            item.setNumber(EncryptionAlgorithmUtil.encrypt(item.getNumber()));
        });

        return recommendArticleVos;
    }
}
