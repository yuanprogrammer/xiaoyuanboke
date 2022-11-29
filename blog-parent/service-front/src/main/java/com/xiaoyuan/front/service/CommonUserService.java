package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.CodeParam;
import com.xiaoyuan.front.vo.param.LoginParam;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.vo.R;

/**
 * FileName:    CommonUserService
 * Author:      小袁
 * Date:        2022/5/1 22:57
 * Description:
 */
public interface CommonUserService extends IService<CommonUser> {

    /**
     * 登陆
     * @return
     */
    R login(LoginParam loginParam);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    R register(LoginParam loginParam);

    /**
     * 通过原始密码修改密码
     * @param loginParam
     * @return
     */
    R modifyPassword(LoginParam loginParam);

    /**
     * 修改昵称或者用户名（用户名为空的情况下）
     * @param token
     * @param commonUserVo
     * @return
     */
    R modifyNickname(String token, CommonUserVo commonUserVo);

    /**
     * 找回密码
     * @param token
     * @param loginParam
     * @return
     */
    R findPassword(String token, LoginParam loginParam);

    /**
     * 修改邮箱或激活邮箱
     * @param codeParam
     * @return
     */
    R modifyEmail(String token, CodeParam codeParam);

    /**
     * 修改手机号码或者号码激活
     * @param token
     * @param codeParam
     * @return
     */
    R modifyMobileNumber(String token, CodeParam codeParam);

    /**
     * 获取用户数据（验证number和token的方式）
     * @return
     */
    R getUserInfo(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    R logout(String token);
}
