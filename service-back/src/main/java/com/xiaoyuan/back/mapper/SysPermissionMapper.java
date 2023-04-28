package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.common.pojo.SysPermission;
import com.xiaoyuan.common.param.role.PermissionRoleParam;
import com.xiaoyuan.common.vo.SysPermissionVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermissionVo> getPermissionList();

    List<SysPermissionVo> getPermissionListByRoleKey(PermissionRoleParam roleParam);
}
