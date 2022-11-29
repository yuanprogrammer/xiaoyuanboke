package com.xiaoyuan.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.param.UserQueryParam;
import com.xiaoyuan.model.vo.CommonUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * FileName:    CommonUserMapper
 * Author:      小袁
 * Date:        2022/4/17 17:25
 * Description: 普通用户DAO
 */
@Repository
public interface CommonUserMapper extends BaseMapper<CommonUser> {

    IPage<CommonUserVo> listCommonUserPage(Page<CommonUserVo> page, @Param("param") UserQueryParam userQueryParam);

    int findTotal();
}
