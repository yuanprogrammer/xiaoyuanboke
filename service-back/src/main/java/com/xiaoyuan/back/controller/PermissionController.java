package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.xiaoyuan.back.service.SysPermissionService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.common.Pagination;
import com.xiaoyuan.model.entity.SysAction;
import com.xiaoyuan.model.entity.SysModule;
import com.xiaoyuan.model.param.permission.*;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.SysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("list")
    @SaCheckPermission(value = {"PERMISSION:SELECT"}, mode = SaMode.OR)
    public R<List<SysPermissionVo>> getPermissionList() {
        return sysPermissionService.getPermissionList();
    }

    @PostMapping("list")
    @SaCheckPermission(value = {"PERMISSION:SELECT"}, mode = SaMode.OR)
    public R<List<SysPermissionVo>> getPermissionListByRoleKey(@RequestBody PermissionRoleParam roleParam) {
        return sysPermissionService.getPermissionListByRoleKey(roleParam);
    }

    @PostMapping("selectModule")
    @SaCheckPermission(value = {"PERMISSION:SELECT"}, mode = SaMode.OR)
    public R<PageVo<List<SysModule>>> selectModule(@RequestBody Pagination pagination) {
        return this.sysPermissionService.selectModuleList(pagination);
    }

    @PostMapping("insertModule")
    @SaCheckPermission(value = {"PERMISSION:INSERT"}, mode = SaMode.OR)
    public R insertModule(@RequestBody @Valid InsertModParam insertModParam) {
        return sysPermissionService.insertModule(insertModParam);
    }

    @PutMapping("updateModule")
    @SaCheckPermission(value = {"PERMISSION:UPDATE"}, mode = SaMode.OR)
    public R updateModule(@RequestBody @Valid UpdateModParam updateModParam) {
        return sysPermissionService.updateModule(updateModParam);
    }

    @DeleteMapping("deleteModule")
    @SaCheckPermission(value = {"PERMISSION:DELETE"}, mode = SaMode.OR)
    public R deleteModule(@RequestBody @Valid DeleteModParam deleteModParam) {
        return sysPermissionService.deleteModule(deleteModParam);
    }

    @PostMapping("selectAction")
    @SaCheckPermission(value = {"PERMISSION:SELECT"}, mode = SaMode.OR)
    public R<PageVo<List<SysAction>>> selectAction(@RequestBody Pagination pagination) {
        return this.sysPermissionService.selectActionList(pagination);
    }

    @PostMapping("insertAction")
    @SaCheckPermission(value = {"PERMISSION:INSERT"}, mode = SaMode.OR)
    public R insertAction(@RequestBody @Valid InsertActParam insertActParam) {
        return sysPermissionService.insertAction(insertActParam);
    }

    @PutMapping("updateAction")
    @SaCheckPermission(value = {"PERMISSION:UPDATE"}, mode = SaMode.OR)
    public R updateAction(@RequestBody @Valid UpdateActParam updateActParam) {
        return sysPermissionService.updateAction(updateActParam);
    }

    @DeleteMapping("deleteAction")
    @SaCheckPermission(value = {"PERMISSION:DELETE"}, mode = SaMode.OR)
    public R deleteAction(@RequestBody @Valid DeleteActParam deleteActParam) {
        return sysPermissionService.deleteAction(deleteActParam);
    }
}
