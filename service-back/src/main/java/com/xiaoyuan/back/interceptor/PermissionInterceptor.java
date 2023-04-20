package com.xiaoyuan.back.interceptor;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.util.SaFoxUtil;
import com.xiaoyuan.back.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * FileName:    PermissionInterceptor
 * Author:      小袁
 * Date:        2022/5/6 9:42
 * Description:
 */
@Component
public class PermissionInterceptor implements StpInterface {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<String> getPermissionList(Object id, String s) {
        Integer userId = Integer.parseInt(id.toString());

        Set<String> permissionSet = sysUserMapper.searchUserPermissions(userId);

        return new ArrayList<>(permissionSet);
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        Integer userId = Integer.parseInt(o.toString());

        Set<String> roleSet = sysUserMapper.searchUserRoles(userId);

        return new ArrayList<>(roleSet);
    }
}
