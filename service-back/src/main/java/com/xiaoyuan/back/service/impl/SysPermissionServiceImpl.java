package com.xiaoyuan.back.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.SysActionMapper;
import com.xiaoyuan.back.mapper.SysModuleMapper;
import com.xiaoyuan.back.mapper.SysPermissionMapper;
import com.xiaoyuan.back.service.SysPermissionService;
import com.xiaoyuan.model.common.PageVo;
import com.xiaoyuan.model.common.Pagination;
import com.xiaoyuan.model.entity.SysAction;
import com.xiaoyuan.model.entity.SysModule;
import com.xiaoyuan.model.entity.SysPermission;
import com.xiaoyuan.model.param.permission.*;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.vo.R;
import com.xiaoyuan.model.vo.SysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysActionMapper sysActionMapper;

    @Override
    public R<List<SysPermissionVo>> getPermissionList() {
        return R.success(this.baseMapper.getPermissionList());
    }

    @Override
    public R<List<SysPermissionVo>> getPermissionListByRoleKey(PermissionRoleParam roleParam) {
        return R.success(this.baseMapper.getPermissionListByRoleKey(roleParam));
    }

    @Override
    public R<PageVo<List<SysModule>>> selectModuleList(Pagination pagination) {
        IPage<SysModule> iPage = this.sysModuleMapper.selectPage(new Page<>(pagination.getPageIndex(), pagination.getPageSize()), null);
        return R.success(new PageVo<>(iPage.getRecords(), iPage.getTotal()));
    }

    @Override
    public R insertModule(InsertModParam insertModParam) {
        SysModule sysModule = new SysModule();
        BeanUtil.copyProperties(insertModParam, sysModule);
        return this.sysModuleMapper.insert(sysModule) != 0 ? R.success() : R.fail();
    }

    @Override
    public R updateModule(UpdateModParam updateModParam) {
        SysModule sysModule = new SysModule();
        BeanUtil.copyProperties(updateModParam, sysModule);
        return this.sysModuleMapper.updateById(sysModule) != 0 ? R.success() : R.fail();
    }

    @Override
    public R deleteModule(DeleteModParam deleteModParam) {
        return this.sysModuleMapper.deleteById(deleteModParam.getId()) != 0 ? R.success() : R.fail();
    }

    @Override
    public R<PageVo<List<SysAction>>> selectActionList(Pagination pagination) {
        IPage<SysAction> iPage = this.sysActionMapper.selectPage(new Page<>(pagination.getPageIndex(), pagination.getPageSize()), null);
        return R.success(new PageVo<>(iPage.getRecords(), iPage.getTotal()));
    }

    @Override
    public R insertAction(InsertActParam insertActParam) {
        SysAction sysAction = new SysAction();
        BeanUtil.copyProperties(insertActParam, sysAction);
        return this.sysActionMapper.insert(sysAction) != 0 ? R.success() : R.fail();
    }

    @Override
    public R updateAction(UpdateActParam updateActParam) {
        SysAction sysAction = new SysAction();
        BeanUtil.copyProperties(updateActParam, sysAction);
        return this.sysActionMapper.updateById(sysAction) != 0 ? R.success() : R.fail();
    }

    @Override
    public R deleteAction(DeleteActParam deleteActParam) {
        return this.sysActionMapper.deleteById(deleteActParam.getId()) != 0 ? R.success() : R.fail();
    }
}
