package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common_util.auth.EncryptionAlgorithmUtil;
import com.xiaoyuan.common_util.convert.DateConverterUtil;
import com.xiaoyuan.front.mapper.ArticleCommentMapper;
import com.xiaoyuan.front.mapper.CommonUserMapper;
import com.xiaoyuan.front.service.ArticleCommentService;
import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.ArticleCommentParam;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.ArticleComment;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.param.article.CommentDeleteParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.article.ArticleCommentVo;
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

//    @Autowired
//    private RabbitTemplate noticeRabbit;

//    @Autowired
//    private SysUserFeign sysUserFeign;

//    @Bean
//    Jackson2JsonMessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

    @Override
    public R insert(ArticleCommentParam articleCommentParam) {
        /**
         * 非法参数校验
         */
        if (articleCommentParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        String articleNumber = articleCommentParam.getArticleNumber();
        String content = articleCommentParam.getContent();
        if (StringUtils.isBlank(articleNumber) || StringUtils.isBlank(content) || articleNumber.length() < 24)
            return R.fail(HttpStatusEnum.PARAM_ERROR);

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
        int result = this.baseMapper.insert(articleComment);

        // 发送通知给后台作者
//        String sysUserCoded = sysUserFeign.searchUserCodeByArticleId(EncryptionAlgorithmUtil.decode(articleCommentParam.getArticleNumber())).getData();
//        SysNoticeVo sysNoticeVo = new SysNoticeVo();
//        sysNoticeVo.setSenderCode(commonUserVo.getComUserCode());
//        sysNoticeVo.setReceiverCode(sysUserCoded);
//        sysNoticeVo.setTitle("评论了该文章");
//        sysNoticeVo.setContent(articleCommentParam.getTitle());
//        sysNoticeVo.setMsgType(1);

//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("text/plain");
//        Message message = new Message(JSONUtil.toJsonStr(sysNoticeVo).getBytes(), messageProperties);
//        noticeRabbit.convertAndSend(MessageQueueConstants.NOTICE_EXCHANGE, MessageQueueConstants.NOTICE_ROUTING, sysNoticeVo);

        return result == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R delete(String token, CommentDeleteParam commentDeleteParam) {
        /**
         * 非法参数校验
         */
        if (StringUtils.isBlank(token)) return R.fail(HttpStatusEnum.PARAM_ERROR);
        // 校验token
        CommonUserVo commonUserVo = tokenService.checkToken(token);

        if (commonUserVo == null) return R.fail(HttpStatusEnum.TOKEN_INVALID);

        return this.baseMapper.deleteById(EncryptionAlgorithmUtil.decode(commentDeleteParam.getNumber())) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R modify(String token, ArticleCommentParam articleCommentParam) {
        /**
         * 非法参数校验
         */
        if (articleCommentParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        String number = articleCommentParam.getNumber();
        String content = articleCommentParam.getContent();
        if (StringUtils.isBlank(number) || StringUtils.isBlank(content) || number.length() < 24)
            return R.fail(HttpStatusEnum.PARAM_ERROR);

        // 校验token
        if (tokenService.checkToken(token) == null) return R.fail(HttpStatusEnum.TOKEN_INVALID);

        // 创建条件对象

        // 修改
        ArticleComment articleComment = new ArticleComment();
        articleComment.setId(Long.parseLong(EncryptionAlgorithmUtil.decode(number)));
        articleComment.setContent(content);
        return this.baseMapper.updateById(articleComment) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R listCommentPage(String token, ArticleLikeParam articleLikeParam) {

        // 创建分页对象
        Page<ArticleComment> page = new Page<>(articleLikeParam.getPageIndex(), articleLikeParam.getPageSize());
        // 创建查询条件对象
        QueryWrapper<ArticleComment> wrapper = new QueryWrapper<>();
        wrapper.select("id", "user_id","parent_id", "content", "gmt_create");
        wrapper.eq("article_id", EncryptionAlgorithmUtil.decode(articleLikeParam.getNumber()));
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

        return R.success(new PageVo<>(list, iPage.getTotal()));
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
