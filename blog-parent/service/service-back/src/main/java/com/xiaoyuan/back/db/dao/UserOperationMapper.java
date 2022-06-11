package com.xiaoyuan.back.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.back.db.entity.UserOperation;
import com.xiaoyuan.back.vo.UserOperationVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * FileName:    UserOperationMapper
 * Author:      小袁教程
 * Date:        2022/5/17 15:47
 * Description:
 */
@Repository
public interface UserOperationMapper extends BaseMapper<UserOperation> {

    List<UserOperationVo> selectUserOperationList(HashMap<String, Object> map);

    int findTotal(HashMap<String, Object> map);

    List<String> selectOperationType();
}
