package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.param.permission.*;
import com.xiaoyuan.common.vo.PageVo;
import com.xiaoyuan.common.vo.Pagination;
import com.xiaoyuan.common.pojo.SysAction;
import com.xiaoyuan.common.pojo.SysModule;
import com.xiaoyuan.common.pojo.SysPermission;
import com.xiaoyuan.common.param.role.PermissionRoleParam;
import com.xiaoyuan.common.vo.R;
import com.xiaoyuan.common.vo.SysPermissionVo;

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
