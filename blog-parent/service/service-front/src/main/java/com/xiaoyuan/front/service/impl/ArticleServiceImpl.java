package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.*;
import com.xiaoyuan.front.db.entity.Article;
import com.xiaoyuan.front.db.entity.ArticleCollect;
import com.xiaoyuan.front.db.entity.CommonUser;
import com.xiaoyuan.front.service.ArticleService;
import com.xiaoyuan.front.service.ThreadService;
import com.xiaoyuan.front.vo.ArchivesVo;
import com.xiaoyuan.front.vo.ArticleVo;
import com.xiaoyuan.front.vo.RecommendArticleVo;
import com.xiaoyuan.front.vo.param.ArticleQueryParam;
import com.xiaoyuan.utils.DateConverterUtil;
import com.xiaoyuan.utils.EncryptionAlgorithmUtil;
import com.xiaoyuan.utils.NumberConverterUtil;
import com.xiaoyuan.utils.StringUtil;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * FileName:    ArticleServiceImpl
 * Author:      小袁
 * Date:        2022/4/24 17:45
 * Description:
 */
@Service
@Transactional
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
    public R listPageArticle(PageUtils pageUtils, ArticleQueryParam queryParam) {
        // 分页参数校验
        int index = pageUtils.getPageIndex();
        int size = pageUtils.getPageSize();
        if (index < 1 || size < 1) {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }else if (size > 11) {
            return R.error(HttpStatusEnum.PARAM_LENGTH_BEYOND);
        }
        // 分页对象
        Page<Article> page = new Page<>(index, size);

        // 搜索词和排序参数校验
        String searchWord = null, sortCondition = null;
        if (queryParam != null) {
            searchWord = queryParam.getSearchWord();
            sortCondition = queryParam.getSortCondition();
        }

        if (StringUtils.isBlank(sortCondition)) {
            sortCondition = "publish_time";
        }else if ("sortTime".equals(sortCondition)) {
            sortCondition = "publish_time";
        }else if ("sortView".equals(sortCondition)) {
            sortCondition = "view_count";
        }else if ("sortLike".equals(sortCondition)) {
            sortCondition = "like_count";
        }else if ("sortCollect".equals(sortCondition)) {
            sortCondition = "collect_count";
        }else {
            sortCondition = "publish_time";
        }

//        IPage<ArticleVo> iPage = null;
//        if (StringUtils.isBlank(searchWord)) {
//            iPage = this.baseMapper.findArticleList(page, sortCondition);
//        }else {
//            iPage = this.baseMapper.findArticleList1(page, searchWord, sortCondition);
//        }
        IPage<ArticleVo> iPage = this.baseMapper.findArticleList(page, searchWord, sortCondition);

        List<ArticleVo> articleVoList = iPage.getRecords();

        articleVoList.forEach(item -> {
            item.setNumber(EncryptionAlgorithmUtil.encrypt(item.getNumber()));
        });
        pageUtils = new PageUtils(articleVoList, iPage.getTotal(), index, size);

        return R.ok().data("articleList", pageUtils);
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
        return R.ok().data("archives", archivesArticleList);
    }

    @Override
    public R listCategoryArticleList(PageUtils pageUtils, Integer id) {
        // 非空校验
        if (id == null || id <= 0) return R.error(HttpStatusEnum.PARAM_ERROR);

        int index = pageUtils.getPageIndex();
        int size = pageUtils.getPageSize();
        if (index <= 0 || size <= 0) return R.error(HttpStatusEnum.PARAM_ERROR);
        if (size > 10) return R.error(HttpStatusEnum.PARAM_LENGTH_BEYOND);

        // 分页对象
        Page<Article> page = new Page<>(index, size);
        IPage<Article> iPage = this.baseMapper.findCategoryArticleList(page, id);
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

        pageUtils = new PageUtils(articleVoList, iPage.getTotal(), index, size);
        return R.ok().data("articleList", pageUtils);
    }

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R getArticleDetailById(String number) {
        if (!StringUtil.checkNumber(number)) return R.error(HttpStatusEnum.PARAM_ERROR);

        long id = Long.parseLong(EncryptionAlgorithmUtil.decode(number));
        ArticleVo articleVo = this.baseMapper.getArticleDetailById(id);

        // 浏览量 + 1
        threadService.updateArticleViewCount(this.baseMapper, id);

        // 分类
        articleVo.setCategoryList(categoryMapper.findArticleCategory(id));
        return R.ok().data("article", articleVo);
    }

    @Override
    public R getArticleMarkdownById(String number) {
        return R.ok().data("content", articleContentMapper.getArticleMarkdownById(EncryptionAlgorithmUtil.decode(number)));
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
