package com.xiaoyuan.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.CommonUserMapper;
import com.xiaoyuan.back.service.CommonUserService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.param.UserQueryParam;
import com.xiaoyuan.model.param.sysuser.SysUserParam;
import com.xiaoyuan.model.vo.CommonUserVo;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * FileName:    CommonUserServiceImpl
 * Author:      小袁
 * Date:        2022/4/17 17:29
 * Description:
 */
@Repository
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser> implements CommonUserService {

    @Autowired
    private CommonUserMapper commonUserMapper;

    @Override
    public CommonUserVo getCommonUserInfoById(Long userId) {
        QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
        wrapper.select("nickname", "avatar");
        wrapper.last("limit 1");

        return copy(commonUserMapper.selectList(wrapper).get(0));
    }

    @Override
    public R<PageVo<List<CommonUserVo>>> selectUserList(UserQueryParam queryParam) {
        IPage<CommonUserVo> userPage = this.baseMapper.listCommonUserPage(new Page<>(queryParam.getPageIndex(), queryParam.getPageSize()), queryParam);

        return R.success(new PageVo<>(userPage.getRecords(), userPage.getTotal()));
    }

    @Override
    public R pullBlackById(SysUserParam sysUserParam) {
        return null;
    }


    private CommonUserVo copy(CommonUser commonUser) {
        CommonUserVo commonUserVo = new CommonUserVo();
        BeanUtils.copyProperties(commonUser, commonUserVo);

        return commonUserVo;
    }
}
