package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.CodeParam;
import com.xiaoyuan.model.param.comuser.LoginParam;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.param.comuser.FindPasswordParam;
import com.xiaoyuan.model.param.comuser.ModifyPasswordParam;
import com.xiaoyuan.model.param.comuser.RegisterParam;
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
     */
    R login(LoginParam loginParam);

    /**
     * 注册
     */
    R register(RegisterParam registerParam);

    /**
     * 通过原始密码修改密码
     */
    R modifyPassword(ModifyPasswordParam modifyPasswordParam);

    /**
     * 修改昵称或者用户名（用户名为空的情况下）
     */
    R modifyNickname(String token, CommonUserVo commonUserVo);

    /**
     * 找回密码
     */
    R findPassword(String token, FindPasswordParam findPasswordParam);

    /**
     * 修改邮箱或激活邮箱
     */
    R modifyEmail(String token, CodeParam codeParam);

    /**
     * 修改手机号码或者号码激活
     */
    R modifyMobileNumber(String token, CodeParam codeParam);

    /**
     * 获取用户数据（验证number和token的方式）
     */
    R getUserInfo(String token);

    /**
     * 退出登录
     */
    R logout(String token);
}
