package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.xiaoyuan.back.service.SysRoleService;
import com.xiaoyuan.common.param.role.DeleteRoleParam;
import com.xiaoyuan.common.param.role.InsertRoleParam;
import com.xiaoyuan.common.param.role.PermissionRoleParam;
import com.xiaoyuan.common.param.role.RoleQueryParam;
import com.xiaoyuan.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("list")
    @SaCheckPermission(value = {"ROLE:SELECT"}, mode = SaMode.OR)
    public R getRoleList(@RequestBody RoleQueryParam queryParam) {
        return sysRoleService.listRolePage(queryParam);
    }

    @PutMapping("updatePermission")
    @SaCheckPermission(value = {"ROLE:UPDATE"}, mode = SaMode.OR)
    public R updatePermission(@RequestBody @Valid PermissionRoleParam permissionRoleParam) {
        return sysRoleService.updatePermission(permissionRoleParam);
    }

    @PostMapping("insertRole")
    @SaCheckPermission(value = {"ROLE:INSERT"}, mode = SaMode.OR)
    public R insertRole(@RequestBody @Valid InsertRoleParam insertRoleParam) {
        return sysRoleService.insertRole(insertRoleParam);
    }

    @DeleteMapping("deleteRole")
    @SaCheckPermission(value = {"ROLE:DELETE"}, mode = SaMode.OR)
    public R deleteRole(@RequestBody @Valid DeleteRoleParam deleteRoleParam) {
        return sysRoleService.deleteRoleById(deleteRoleParam);
    }
}
