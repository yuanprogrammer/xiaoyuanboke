package com.xiaoyuan.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common_util.auth.EncryptionAlgorithmUtil;
import com.xiaoyuan.common_util.match.StringMatch;
import com.xiaoyuan.front.mapper.ArticleLikeMapper;
import com.xiaoyuan.front.service.ArticleLikeService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.model.entity.ArticleLike;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.article.ArticleLikeParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.article.ArticleOperationVo;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.model.constants.RedisConstantKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
    public R likeArticle(ArticleLikeParam articleLikeParam) {
        CommonUserVo commonUserVo = UserThreadLocal.get();

        long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        long articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(articleLikeParam.getNumber()));

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

        Map<String, Object> map = new HashMap<>();
        map.put("like", operationVo);

        return R.success(map);
    }

    @Override
    public R getLikeInfo(String token, ArticleLikeParam articleLikeParam) {
        ArticleOperationVo operationVo = new ArticleOperationVo();
        long userId, articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(articleLikeParam.getNumber()));;

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
        Map<String, Object> map = new HashMap<>();
        map.put("like", operationVo);

        return R.success(map);
    }
}
