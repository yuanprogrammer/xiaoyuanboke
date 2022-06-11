package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.ArticleCommentMapper;
import com.xiaoyuan.front.db.dao.ArticleMapper;
import com.xiaoyuan.front.db.dao.CommonUserMapper;
import com.xiaoyuan.front.db.entity.Article;
import com.xiaoyuan.front.db.entity.ArticleComment;
import com.xiaoyuan.front.db.entity.CommonUser;
import com.xiaoyuan.front.service.ArticleCommentService;
import com.xiaoyuan.front.service.ThreadService;
import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.ArticleCommentVo;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.utils.DateConverterUtil;
import com.xiaoyuan.utils.EncryptionAlgorithmUtil;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName:    ArticleCommentServiceImpl
 * Author:      小袁
 * Date:        2022/5/3 19:30
 * Description:
 */
@Service
@Transactional
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CommonUserMapper commonUserMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public R insert(ArticleCommentParam articleCommentParam) {
        /**
         * 非法参数校验
         */
        if (articleCommentParam == null) return R.error(HttpStatusEnum.PARAM_ERROR);
        String articleNumber = articleCommentParam.getArticleNumber();
        String content = articleCommentParam.getContent();
        if (StringUtils.isBlank(articleNumber) || StringUtils.isBlank(content) || articleNumber.length() < 24)
            return R.error(HttpStatusEnum.PARAM_ERROR);

        // 获取被拦截的用户信息
        CommonUserVo commonUserVo = UserThreadLocal.get();
        // 创建评论实体, 插入数据
        ArticleComment articleComment = new ArticleComment();
        // 文章编号
        articleComment.setArticleId(Long.parseLong(EncryptionAlgorithmUtil.decode(articleNumber)));
        // 用户编号
        articleComment.setUserId(Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber())));
        // 评论内容
        articleComment.setContent(content);
        // 插入
        return this.baseMapper.insert(articleComment) == 0 ? R.error(HttpStatusEnum.UNKNOWN_ERROR) : R.ok();
    }

    @Override
    public R delete(String token, String number) {
        /**
         * 非法参数校验
         */
        if (StringUtils.isBlank(token) || StringUtils.isBlank(number) || number.length() < 24) return R.error(HttpStatusEnum.PARAM_ERROR);
        // 校验token
        CommonUserVo commonUserVo = tokenService.checkToken(token);

        if (commonUserVo == null) return R.error(HttpStatusEnum.TOKEN_INVALID);

        return this.baseMapper.deleteById(EncryptionAlgorithmUtil.decode(number)) == 0 ? R.error(HttpStatusEnum.UNKNOWN_ERROR) : R.ok();
    }

    @Override
    public R modify(String token, ArticleCommentParam articleCommentParam) {
        /**
         * 非法参数校验
         */
        if (articleCommentParam == null) return R.error(HttpStatusEnum.PARAM_ERROR);
        String number = articleCommentParam.getNumber();
        String content = articleCommentParam.getContent();
        if (StringUtils.isBlank(number) || StringUtils.isBlank(content) || number.length() < 24)
            return R.error(HttpStatusEnum.PARAM_ERROR);

        // 校验token
        if (tokenService.checkToken(token) == null) return R.error(HttpStatusEnum.TOKEN_INVALID);

        // 创建条件对象

        // 修改
        ArticleComment articleComment = new ArticleComment();
        articleComment.setId(Long.parseLong(EncryptionAlgorithmUtil.decode(number)));
        articleComment.setContent(content);
        return this.baseMapper.updateById(articleComment) == 0 ? R.error(HttpStatusEnum.UNKNOWN_ERROR) : R.ok();
    }

    @Override
    public R listCommentPage(String token, PageUtils pageUtils, String articleNumber) {
        if (pageUtils == null || articleNumber == null || articleNumber.length() < 24) return R.error(HttpStatusEnum.PARAM_ERROR);
        int index = pageUtils.getPageIndex(); // 当前页
        int size = pageUtils.getPageSize(); // 页数据大小

        if (index <= 0 || size <= 0) return R.error(HttpStatusEnum.PARAM_ERROR);
        if (size > 10) return R.error(HttpStatusEnum.PARAM_LENGTH_BEYOND);

        // 创建分页对象
        Page<ArticleComment> page = new Page<>(index, size);
        // 创建查询条件对象
        QueryWrapper<ArticleComment> wrapper = new QueryWrapper<>();
        wrapper.select("id", "user_id","parent_id", "content", "gmt_create");
        wrapper.eq("article_id", EncryptionAlgorithmUtil.decode(articleNumber));
        wrapper.orderByDesc("gmt_create");

        // 查询
        IPage<ArticleComment> iPage = this.baseMapper.selectPage(page, wrapper);

        // 查询当前是否有用户信息
        String userNumber;
        CommonUserVo commonUserVo = tokenService.checkToken(token);
        if (commonUserVo != null) {
            userNumber = commonUserVo.getNumber();
        }else {
            userNumber = "0";
        }
        // 封装
        final String number = userNumber;
        List<ArticleCommentVo> list = new ArrayList<>();
        iPage.getRecords().forEach(item -> {
            list.add(copy(item, number));
        });

        pageUtils = new PageUtils(list, iPage.getTotal(), index, size);
        return R.ok().data("commentList", pageUtils);
    }

    private ArticleCommentVo copy(ArticleComment articleComment, String userNumber) {
        ArticleCommentVo articleCommentVo = new ArticleCommentVo();
        articleCommentVo.setNumber(EncryptionAlgorithmUtil.encrypt(String.valueOf(articleComment.getId())));
        articleCommentVo.setContent(articleComment.getContent());
        articleCommentVo.setCommentTime(DateConverterUtil.toStringFromDate(articleComment.getGmtCreate(), true));
        // 查询用户昵称和头像
        QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
        wrapper.select("avatar", "nickname");
        wrapper.eq("id", articleComment.getUserId());
        wrapper.last("limit 1");
        CommonUser commonUser = commonUserMapper.selectOne(wrapper);
        // 封装到评论对象中
        articleCommentVo.setAvatar(commonUser.getAvatar());
        articleCommentVo.setNickname(commonUser.getNickname());
        // 判断是否本人的评论
        if (userNumber.equals(EncryptionAlgorithmUtil.encrypt(String.valueOf(articleComment.getUserId())))) {
            articleCommentVo.setStatus("1");
        }else {
            articleCommentVo.setStatus("0");
        }
        // 回传
        return articleCommentVo;
    }
}
