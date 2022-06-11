package com.xiaoyuan.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.ArticleLikeMapper;
import com.xiaoyuan.front.db.entity.ArticleLike;
import com.xiaoyuan.front.service.ArticleLikeService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.ArticleOperationVo;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.utils.EncryptionAlgorithmUtil;
import com.xiaoyuan.utils.JWTUtil;
import com.xiaoyuan.utils.StringUtil;
import com.xiaoyuan.utils.constant.CookieConstant;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.constant.RedisConstantKey;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:    ArticleLikeServiceImpl
 * Author:      小袁
 * Date:        2022/5/11 9:41
 * Description:
 */
@Service
@Transactional
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Override
    public R likeArticle(String number) {
        if (!StringUtil.checkNumber(number)) {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }

        CommonUserVo commonUserVo = UserThreadLocal.get();

        long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        long articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(number));

        ArticleOperationVo operationVo = new ArticleOperationVo();

        if (this.baseMapper.isContainLike(articleId, userId)) {
            this.baseMapper.removeLike(articleId, userId);
            operationVo.setStatus(false);
        } else {
            ArticleLike articleLike = new ArticleLike();
            articleLike.setUserId(userId);
            articleLike.setArticleId(articleId);
            operationVo.setStatus(true);
            this.baseMapper.insert(articleLike);
        }

        // 发现文章点赞数量
        operationVo.setLikeCount(this.baseMapper.findCountByArticleId(articleId));
        return R.ok().data("like", operationVo);
    }

    @Override
    public R getLikeInfo(String token, String number) {
        if (!StringUtil.checkNumber(number)) return R.error(HttpStatusEnum.PARAM_ERROR);

        ArticleOperationVo operationVo = new ArticleOperationVo();
        long userId, articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(number));;

        if (StringUtils.isBlank(token)) {
            operationVo.setStatus(false);
        }else {
            CommonUserVo commonUserVo = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstantKey.USER + token), CommonUserVo.class);
            if (commonUserVo == null) {
                operationVo.setStatus(false);
            }else {
                userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
                if (this.baseMapper.isContainLike(articleId, userId)) {
                    operationVo.setStatus(true);
                }else {
                    operationVo.setStatus(false);
                }
            }
        }

        operationVo.setLikeCount(this.baseMapper.findCountByArticleId(articleId));
        return R.ok().data("like", operationVo);
    }
}