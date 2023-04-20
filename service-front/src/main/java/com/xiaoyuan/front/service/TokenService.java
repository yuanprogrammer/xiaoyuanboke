package com.xiaoyuan.front.service;

import com.xiaoyuan.front.vo.CommonUserVo;

/**
 * FileName:    TokenService
 * Author:      小袁
 * Date:        2022/5/3 9:51
 * Description:
 */
public interface TokenService {

    /**
     * 检查token合法性
     */
    CommonUserVo checkToken(String token);
}
