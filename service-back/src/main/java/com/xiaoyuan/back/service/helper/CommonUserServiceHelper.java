package com.xiaoyuan.back.service.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoyuan.back.mapper.CommonUserMapper;
import com.xiaoyuan.common.pojo.CommonUser;
import com.xiaoyuan.common.vo.CommonUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName:    CommonServiceHelper
 * Author:      小袁
 * Date:        2022/4/18 17:37
 * Description:
 */
@Service
public class CommonUserServiceHelper {

    @Autowired
    private CommonUserMapper commonUserMapper;

    /**
     * 根据用户编号查询用户昵称
     * @param userId
     * @return
     */
    public CommonUserVo getCommonUserInfoById(Long userId) {
        QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
        wrapper.select("nickname", "avatar");
        wrapper.last("limit 1");

        return copy(commonUserMapper.selectList(wrapper).get(0));
    }

    private CommonUserVo copy(CommonUser commonUser) {
        CommonUserVo commonUserVo = new CommonUserVo();
        BeanUtils.copyProperties(commonUser, commonUserVo);

        return commonUserVo;
    }
}
