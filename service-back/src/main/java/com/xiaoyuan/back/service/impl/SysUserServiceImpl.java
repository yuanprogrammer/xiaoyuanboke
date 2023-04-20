package com.xiaoyuan.back.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.SysUserMapper;
import com.xiaoyuan.back.service.SysUserService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.constants.defaults.SystemDefaultConstants;
import com.xiaoyuan.model.entity.SysUser;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.sysuser.SysUserLoginParam;
import com.xiaoyuan.model.param.sysuser.SysUserQueryParam;
import com.xiaoyuan.model.param.sysuser.WeChatRegisterParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.sysuser.SysUserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public R login(SysUserLoginParam loginParam) {
        // 判断用户是否存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, loginParam.getUsername());

        if (this.baseMapper.selectOne(wrapper) == null) {
            return R.fail(HttpStatusEnum.USER_NOT_EXIST);
        }

        wrapper.eq(SysUser::getPassword, DigestUtil.md5Hex(loginParam.getPassword()));
        SysUser sysUser = this.baseMapper.selectOne(wrapper);

        if (sysUser == null) {
            return R.fail(HttpStatusEnum.PASSWORD_ERROR);
        }

        StpUtil.login(sysUser.getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenInfo().getTokenValue());
//        map.put("permissions", baseMapper.searchUserPermissions(sysUser.getId()));
        return R.success(map);
    }

    @Override
    public R getInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();

        SysUserVo sysUserVo = this.baseMapper.getInfoById(userId);

        if (sysUserVo == null) {
            return R.fail(HttpStatusEnum.USER_NOT_EXIST);
        }

        sysUserVo.setPermissions(this.baseMapper.searchUserPermissions(userId));
        return R.success().data(sysUserVo);
    }

    @Override
    public boolean isBindingPhone(String openId) {
        return openId == null;
    }

    @Override
    public boolean isExistUserByCode(String sysUserCode) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysUser::getId);
        wrapper.eq(SysUser::getSysUserCode, sysUserCode);
        wrapper.last("limit 1");
        return this.baseMapper.selectOne(wrapper) != null;
    }

    @Override
    public SysUser getSysUserByOpenId(SysUserLoginParam loginParam) {
        // 判断用户是否存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysUser::getId);
        wrapper.eq(SysUser::getOpenId, loginParam.getOpenId());
        wrapper.last("limit 1");

        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public String searchUserCodeByArticleId(String articleId) {
        return this.baseMapper.searchUserCodeByArticleId(articleId);
    }

    @Override
    public int insertSysUserByOpenId(WeChatRegisterParam weChatRegisterParam) {
        SysUser sysUser = new SysUser();
        sysUser.setOpenId(weChatRegisterParam.getOpenId());
        sysUser.setAvatar(SystemDefaultConstants.SYS_USER_AVATAR);
        sysUser.setSysUserCode(SystemDefaultConstants.SYS_USER_CODE);
        sysUser.setRoleId(SystemDefaultConstants.SYS_USER_ROLE_ID);
        sysUser.setNickname(SystemDefaultConstants.SYS_USER_NICKNAME);
        sysUser.setState(SystemDefaultConstants.SYS_USER_STATE);
        return this.baseMapper.insert(sysUser);
    }

    @Override
    public R<PageVo<List<SysUserVo>>> getSysUserList(SysUserQueryParam sysUserQueryParam) {
        IPage<SysUserVo> page = this.baseMapper.getSysUserList(new Page<>(sysUserQueryParam.getPageIndex(), sysUserQueryParam.getPageSize()), sysUserQueryParam);
        return R.success(new PageVo<>(page.getRecords(), page.getTotal()));
    }
}
