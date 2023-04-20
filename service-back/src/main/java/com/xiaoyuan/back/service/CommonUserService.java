package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.param.UserQueryParam;
import com.xiaoyuan.model.param.sysuser.SysUserParam;
import com.xiaoyuan.model.vo.CommonUserVo;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;

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
