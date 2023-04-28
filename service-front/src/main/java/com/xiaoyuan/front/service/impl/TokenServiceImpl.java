package com.xiaoyuan.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaoyuan.common.util.JWTUtil;
import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.common.constants.RedisConstantKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * FileName:    TokenServiceImpl
 * Author:      小袁
 * Date:        2022/5/3 9:51
 * Description:
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public CommonUserVo checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        Map<String, Object> map = JWTUtil.checkToken(token);
        if (null == map) {
            return null;
        }

        String userJSON = redisTemplate.opsForValue().get(RedisConstantKey.USER + token);
        if (StringUtils.isBlank(userJSON)) {
            return null;
        }

        CommonUserVo commonUserVo = JSON.parseObject(userJSON, CommonUserVo.class);
        return commonUserVo;
    }
}
