package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.SysRoleMapper;
import com.xiaoyuan.back.service.SysRoleService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.SysRole;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.param.role.DeleteRoleParam;
import com.xiaoyuan.model.param.role.InsertRoleParam;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.param.role.RoleQueryParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.SysRoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public R<PageVo<List<SysRoleVo>>> listRolePage(RoleQueryParam queryParam) {
        IPage<SysRoleVo> rolePage = this.baseMapper.listRolePage(new Page<>(queryParam.getPageIndex(), queryParam.getPageSize()), queryParam);
        return R.success(new PageVo<>(rolePage.getRecords(), rolePage.getTotal()));
    }

    @Override
    public R updatePermission(PermissionRoleParam permissionRoleParam) {
        return this.baseMapper.updatePermissions(permissionRoleParam) != 0 ? R.success() : R.fail();
    }

    @Override
    public R insertRole(InsertRoleParam insertRoleParam) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysRole::getId);
        wrapper.eq(SysRole::getRoleKey, insertRoleParam.getRoleKey());
        wrapper.last("limit 1");
        if (this.baseMapper.selectOne(wrapper) != null) {
            return R.fail(HttpStatusEnum.ROLE_ALREALD_EXIST);
        }

        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(insertRoleParam.getRoleKey());
        sysRole.setRoleName(insertRoleParam.getRoleName());
        sysRole.setDescribe(insertRoleParam.getDescribe());
        sysRole.setPermissions(insertRoleParam.getPermissions());
        return this.baseMapper.insertRole(sysRole) != 0 ? R.success() : R.fail();
    }

    @Override
    public R deleteRoleById(DeleteRoleParam deleteRoleParam) {
        return this.baseMapper.deleteById(deleteRoleParam.getId()) != 0 ? R.success() : R.fail();
    }
}
