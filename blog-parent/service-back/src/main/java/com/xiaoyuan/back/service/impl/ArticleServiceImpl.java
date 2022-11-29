package com.xiaoyuan.back.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.*;
import com.xiaoyuan.back.service.ArticleService;
import com.xiaoyuan.back.service.CategoryService;
import com.xiaoyuan.back.service.helper.CommonUserServiceHelper;
import com.xiaoyuan.common_util.convert.TextOperation;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.Article;
import com.xiaoyuan.model.entity.ArticleCategory;
import com.xiaoyuan.model.entity.ArticleContent;
import com.xiaoyuan.model.param.ArticleParam;
import com.xiaoyuan.model.param.article.ArticleQueryParam;
import com.xiaoyuan.model.vo.article.ArticlePublishVo;
import com.xiaoyuan.model.vo.article.ArticleVo;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * FileName:    ArticleServiceImpl
 * Author:      小袁
 * Date:        2022/4/16 11:24
 * Description:
 */
@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 文章表DAO
     */
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 文章表和文章分类表中间表DAO
     */
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    /**
     * 文章表和文章内容表的中间表DAO
     */
    @Autowired
    private ArticleContentMapper articleContentMapper;

    /**
     * 分类业务层
     */
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommonUserServiceHelper commonUserServiceHelper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public R<String> insert(ArticleParam articleParam) {
        Article article = new Article();
        article.setTitle(articleParam.getTitle()); // 标题
        article.setCover(articleParam.getCover()); // 封面
        // 提取摘要
        if (articleParam.getDigest() != null) {
            article.setDigest(articleParam.getDigest());
        }else {
            String digest = TextOperation.getTextFromHtml(articleParam.getArticleContentParam().getContentHtml());
            article.setDigest(TextOperation.getArticleDigestFromText(digest)); // 摘要
        }
        article.setTags(articleParam.getTags()); // 标签
        if (StrUtil.isNotEmpty(articleParam.getUserId())) {
            article.setAuthorId(Long.parseLong(articleParam.getUserId()));
        }

        // 插入数据, 获取回传的文章编号（MyBatis-Plus内部封装）
        articleMapper.insert(article);

        /**
         * 插入分类专栏数据到中间表
         * [1,2,3] --> 循环插入
         */
        List<Integer> categoryList = articleParam.getCategoryList();
        for (Integer categoryId : categoryList) {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setCategoryId(categoryId);
            articleCategory.setArticleId(article.getId());
            articleCategoryMapper.insert(articleCategory);
        }

        // 插入markdown文本和HTML格式文本数据到中间表
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(article.getId());
        articleContent.setContent(articleParam.getArticleContentParam().getContent());
        articleContent.setContentHtml(articleParam.getArticleContentParam().getContentHtml());
        articleContentMapper.insert(articleContent);

        return R.success("发布成功！");
    }

    @Override
    public R<PageVo<List<ArticleVo>>> listArticlePage(ArticleQueryParam articleQueryParam) {
        Page<ArticleVo> page = new Page<>(articleQueryParam.getPageIndex(), articleQueryParam.getPageSize());
        IPage<ArticleVo> iPage = this.baseMapper.listArticlePage(page, articleQueryParam);

        List<ArticleVo> articleVoList = iPage.getRecords();
        articleVoList.forEach(item -> {
            item.setCategoryList(categoryService.findCategoryNamesByArticleId(Long.parseLong(item.getId())));
        });

        return R.success(new PageVo<>(articleVoList, iPage.getTotal()));
    }

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleLikeMapper articleLikeMapper;

    @Autowired
    private ArticleCollectMapper articleCollectMapper;


    @Override
    public R removeArticleById(Long articleId) {
        // 先删除文章
        int deleteArticleResult = articleMapper.deleteById(articleId);

        // 再从 文章-内容中间表 删除文章内容
        QueryWrapper<ArticleContent> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        int deleteContentResult = articleContentMapper.delete(wrapper);

        // 再从 文章-分类中间表 删除文章所属分类
        QueryWrapper<ArticleCategory> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("article_id", articleId);
        articleCategoryMapper.delete(wrapper1);

        // 删除文章留言
        articleCommentMapper.removeByArticleId(articleId);

        // 删除收藏
        articleCollectMapper.removeByArticleId(articleId);

        // 删除点赞
        articleLikeMapper.removeByArticleId(articleId);

        return deleteArticleResult == 0 || deleteContentResult == 0 ? R.fail("文章删除失败！") : R.success("文章成功删除！");
    }

    @Override
    public R getArticleDetailById(Long articleId) {
        // 构造条件对象, 设置article_id = articleId
        QueryWrapper<ArticleContent> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);

        // 筛选查询结果, 查找HTML格式文本, 限制只查询一条数据, 提高效率
        wrapper.select("content");
        wrapper.last("limit 1");

        // 调用DAO
        List<ArticleContent> articleContents = articleContentMapper.selectList(wrapper);
        if (articleContents.size() <= 0) {
            return R.fail("文章不存在");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("articleContent", articleContents.get(0).getContent());
        return R.success(map);
    }

    @Override
    public R getArticlePublishById(Long articleId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("title", "digest", "cover", "tags");
        wrapper.eq("id", articleId);
        wrapper.last("limit 1");
        Article article = articleMapper.selectOne(wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("articlePublish", copy(article, articleId));
        return R.success(map);
    }

    @Override
    public R modifyArticleById(ArticleParam articleParam) {
        // 文章编号
        Long articleId = Long.parseLong(articleParam.getId());

        // 修改文章表
        Article article = new Article();
        article.setId(articleId);
        BeanUtils.copyProperties(articleParam, article);
        int modifyArticleResult = articleMapper.updateById(article);

        // 修改 文章-内容 中间表
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleParam.getArticleContentParam().getContent());
        articleContent.setContentHtml(articleParam.getArticleContentParam().getContentHtml());
        // 设置条件 article_id = id
        QueryWrapper<ArticleContent> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        int modifyArticleContentResult = articleContentMapper.update(articleContent, wrapper);

        // 修改 文章-分类 中间表
        // 先删除原有的分类
        QueryWrapper<ArticleCategory> articleCategoryQueryWrapper = new QueryWrapper<>();
        // 构造删除条件 article_id = id
        articleCategoryQueryWrapper.eq("article_id", articleId);
        articleCategoryMapper.delete(articleCategoryQueryWrapper);
        // 再添加新的分类列表
        List<Integer> categoryIds = articleParam.getCategoryList();
        for (Integer categoryId : categoryIds) {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(categoryId);
            articleCategoryMapper.insert(articleCategory);
        }

        return modifyArticleResult == 0 || modifyArticleContentResult == 0 ? R.fail("修改失败！") : R.success("文章修改成功！");
    }

    private ArticlePublishVo copy(Article article, Long id) {
        ArticlePublishVo articlePublishVo = new ArticlePublishVo();
        // 查询文章所属分类 return 分类编号列表
        List<Integer> categoryIds = articleCategoryMapper.findCategoryIdsByArticleId(id);
        if (categoryIds.size() == 0) articlePublishVo.setCategorySelected(null);
        else {
            // 通过分类编号列表查询每个分类完整路径, 用于selected
            List<String> list = categoryMapper.getCompleteCategoryByIds(categoryIds);
            // 存储, 文章分类选中列表
            List<List<String>> categorySelected = new ArrayList<>();
            for (String s : list) {
                List<String> splitCategory = new ArrayList<>();
                // 切割 1-2-12 -->> 1 2 12
                String[] arr = s.split("-");
                // 转换 arr(数组) -->> list(集合)
                Collections.addAll(splitCategory, arr);
                categorySelected.add(splitCategory);
            }
            articlePublishVo.setCategorySelected(categorySelected);
        }
        // 查询详细内容
        articlePublishVo.setContent(articleContentMapper.getArticleMarkdownById(id));
        // 拷贝
        BeanUtils.copyProperties(article, articlePublishVo);
        return articlePublishVo;
    }
}
