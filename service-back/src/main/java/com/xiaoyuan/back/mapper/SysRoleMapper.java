package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.common.pojo.SysRole;
import com.xiaoyuan.common.param.role.PermissionRoleParam;
import com.xiaoyuan.common.param.role.RoleQueryParam;
import com.xiaoyuan.common.vo.SysRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRoleVo> listRolePage(Page<SysRoleVo> page, @Param("param") RoleQueryParam roleQueryParam);

    int updatePermissions(PermissionRoleParam permissionRoleParam);

    int insertRole(SysRole SysRole);
}
