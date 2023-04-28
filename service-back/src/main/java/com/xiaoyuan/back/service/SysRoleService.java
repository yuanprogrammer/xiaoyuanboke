package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.vo.PageVo;
import com.xiaoyuan.common.pojo.SysRole;
import com.xiaoyuan.common.param.role.DeleteRoleParam;
import com.xiaoyuan.common.param.role.InsertRoleParam;
import com.xiaoyuan.common.param.role.PermissionRoleParam;
import com.xiaoyuan.common.param.role.RoleQueryParam;
import com.xiaoyuan.common.vo.R;
import com.xiaoyuan.common.vo.SysRoleVo;

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
