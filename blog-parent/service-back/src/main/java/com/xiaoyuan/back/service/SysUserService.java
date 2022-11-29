package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.SysUser;
import com.xiaoyuan.model.param.sysuser.SysUserLoginParam;
import com.xiaoyuan.model.param.sysuser.SysUserParam;
import com.xiaoyuan.model.param.sysuser.SysUserQueryParam;
import com.xiaoyuan.model.param.sysuser.WeChatRegisterParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.sysuser.SysUserVo;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    R login(SysUserLoginParam loginParam);

    /**
     * 获取用户信息
     */
    R getInfo();

    /**
     * 根据openId查询是否绑定了手机号码
     */
    boolean isBindingPhone(String openId);

    /**
     * 通过唯一编号判断是否存在用户
     */
    boolean isExistUserByCode(String sysUserCode);

    /**
     * 通过微信opeId查询用户
     */
    SysUser getSysUserByOpenId(SysUserLoginParam loginParam);

    /**
     *
     */
    String searchUserCodeByArticleId(String articleId);

    /**
     * 微信首次登录注册
     */
    int insertSysUserByOpenId(WeChatRegisterParam weChatRegisterParam);

    /**
     * 获取系统用户列表
     */
    R<PageVo<List<SysUserVo>>> getSysUserList(SysUserQueryParam sysUserQueryParam);
}
