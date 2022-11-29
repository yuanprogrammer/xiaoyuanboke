package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.model.entity.SysPermission;
import com.xiaoyuan.model.param.role.PermissionRoleParam;
import com.xiaoyuan.model.vo.SysPermissionVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermissionVo> getPermissionList();

    List<SysPermissionVo> getPermissionListByRoleKey(PermissionRoleParam roleParam);
}
