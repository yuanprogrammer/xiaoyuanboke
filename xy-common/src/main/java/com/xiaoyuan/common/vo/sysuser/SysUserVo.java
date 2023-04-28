package com.xiaoyuan.common.vo.sysuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xiaoyuan.common.vo.SysRoleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserVo {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 编号
     */
    private String sysUserCode;

    /**
     * 状态
     */
    private String state;

    /**
     * 发布文章数量
     */
    private String articleCount;

    /**
     * 角色集合
     */
    private List<SysRoleVo> roles;

    /**
     * 权限集合
     */
    private Set<String> permissions;
}
