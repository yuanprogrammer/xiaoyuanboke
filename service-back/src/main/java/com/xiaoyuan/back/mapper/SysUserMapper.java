package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.common.pojo.SysUser;
import com.xiaoyuan.common.param.sysuser.SysUserQueryParam;
import com.xiaoyuan.common.vo.sysuser.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUserVo> getSysUserList(Page<SysUserVo> page, @Param("param") SysUserQueryParam sysUserQueryParam);

    SysUserVo getInfoById(Integer id);

    Set<String> searchUserPermissions(Integer id);

    Set<String> searchUserRoles(Integer id);

    String searchUserCodeByArticleId(String articleId);
}
