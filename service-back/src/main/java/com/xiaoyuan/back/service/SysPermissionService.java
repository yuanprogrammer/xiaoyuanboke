package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.common.Pagination;
import com.xiaoyuan.model.entity.SysAction;
import com.xiaoyuan.model.entity.SysModule;
import com.xiaoyuan.model.entity.SysPermission;
import com.xiaoyuan.model.param.permission.*;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.SysPermissionVo;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    R<List<SysPermissionVo>> getPermissionList();

    R<List<SysPermissionVo>> getPermissionListByRoleKey(PermissionRoleParam roleParam);

    R<PageVo<List<SysModule>>> selectModuleList(Pagination pagination);

    R insertModule(InsertModParam insertModParam);

    R updateModule(UpdateModParam updateModParam);

    R deleteModule(DeleteModParam deleteModParam);

    R<PageVo<List<SysAction>>> selectActionList(Pagination pagination);

    R insertAction(InsertActParam insertActParam);

    R updateAction(UpdateActParam updateActParam);

    R deleteAction(DeleteActParam deleteActParam);
}
