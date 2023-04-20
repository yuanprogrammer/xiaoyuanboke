package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.entity.SysRole;
import com.xiaoyuan.model.param.role.DeleteRoleParam;
import com.xiaoyuan.model.param.role.InsertRoleParam;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.param.role.RoleQueryParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.SysRoleVo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    R<PageVo<List<SysRoleVo>>> listRolePage(RoleQueryParam queryParam);

    R updatePermission(PermissionRoleParam permissionRoleParam);

    /**
     * 新加角色
     */
    R insertRole(InsertRoleParam insertRoleParam);

    /**
     * 删除角色
     */
    R deleteRoleById(DeleteRoleParam deleteRoleParam);
}
