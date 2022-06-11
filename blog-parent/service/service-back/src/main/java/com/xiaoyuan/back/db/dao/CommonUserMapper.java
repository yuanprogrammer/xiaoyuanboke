package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.back.db.entity.CommonUser;
import com.xiaoyuan.back.vo.CommonUserVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * FileName:    CommonUserMapper
 * Author:      小袁
 * Date:        2022/4/17 17:25
 * Description: 普通用户DAO
 */
@Repository
public interface CommonUserMapper extends BaseMapper<CommonUser> {

    List<CommonUserVo> selectUserList(HashMap hashMap);

    int findTotal();
}
