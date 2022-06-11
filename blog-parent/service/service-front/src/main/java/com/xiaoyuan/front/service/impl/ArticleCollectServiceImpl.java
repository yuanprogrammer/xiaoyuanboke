package com.xiaoyuan.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.ArticleCollectMapper;
import com.xiaoyuan.front.db.entity.ArticleCollect;
import com.xiaoyuan.front.service.ArticleCollectService;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.ArticleOperationVo;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.utils.EncryptionAlgorithmUtil;
import com.xiaoyuan.utils.StringUtil;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.constant.RedisConstantKey;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:    ArticleCollectServiceImpl
 * Author:      小袁
 * Date:        2022/5/11 9:28
 * Description:
 */
@Service
@Transactional
public class ArticleCollectServiceImpl extends ServiceImpl<ArticleCollectMapper, ArticleCollect> implements ArticleCollectService {

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Override
    public R collectArticle(String number) {
        // 校验编号
        if (StringUtil.checkNumber(number)) {
            CommonUserVo commonUserVo = UserThreadLocal.get();

            long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
            long articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(number));

            ArticleOperationVo operationVo = new ArticleOperationVo();

            if (this.baseMapper.isContainCollect(articleId, userId)) {
                this.baseMapper.removeCollect(articleId, userId);
                operationVo.setStatus(false);
            } else {
                ArticleCollect articleCollect = new ArticleCollect();
                articleCollect.setUserId(userId);
                articleCollect.setArticleId(articleId);
                this.baseMapper.insert(articleCollect);
                operationVo.setStatus(true);
            }

            operationVo.setCollectCount(this.baseMapper.findCountByArticleId(articleId));
            return R.ok().data("collect", operationVo);
        } else {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }

    }

    @Override
    public R getCollectInfo(String token, String number) {
        if (!StringUtil.checkNumber(number)) return R.error(HttpStatusEnum.PARAM_ERROR);

        ArticleOperationVo operationVo = new ArticleOperationVo();
        long userId, articleId = Long.parseLong(EncryptionAlgorithmUtil.decode(number));

        if (StringUtils.isBlank(token)) {
            operationVo.setStatus(false);
        }else {
            CommonUserVo commonUserVo = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstantKey.USER + token), CommonUserVo.class);
            if (commonUserVo == null) {
                operationVo.setStatus(false);
            }else {
                userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
                if (this.baseMapper.isContainCollect(articleId, userId)) {
                    operationVo.setStatus(true);
                }else {
                    operationVo.setStatus(false);
                }
            }
        }

        operationVo.setCollectCount(this.baseMapper.findCountByArticleId(articleId));
        return R.ok().data("collect", operationVo);
    }
}
