package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.vo.PageVo;
import com.xiaoyuan.common.pojo.CommonUser;
import com.xiaoyuan.common.param.UserQueryParam;
import com.xiaoyuan.common.param.sysuser.SysUserParam;
import com.xiaoyuan.common.vo.CommonUserVo;
import com.xiaoyuan.common.vo.R;

import java.util.List;

/**
 * FileName:    CommonUserService
 * Author:      小袁
 * Date:        2022/4/17 17:29
 * Description:
 */
public interface CommonUserService extends IService<CommonUser> {

    /**
     * 根据用户编号查询用户昵称
     * @param userId
     * @return
     */
    CommonUserVo getCommonUserInfoById(Long userId);

    /**
     * 分页查询
     */
    R<PageVo<List<CommonUserVo>>> selectUserList(UserQueryParam queryParam);

    /**
     * 加入黑名单
     */
    R pullBlackById(SysUserParam sysUserParam);
}
