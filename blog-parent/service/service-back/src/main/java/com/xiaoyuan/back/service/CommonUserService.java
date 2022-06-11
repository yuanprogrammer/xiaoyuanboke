package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.back.db.entity.CommonUser;
import com.xiaoyuan.back.vo.CommonUserVo;
import com.xiaoyuan.back.vo.param.UserQueryParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

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

    R selectUserList(PageUtils pageUtils, UserQueryParam queryParam);
}
