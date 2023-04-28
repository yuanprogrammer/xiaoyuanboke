package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.pojo.UserOperation;
import com.xiaoyuan.common.param.UserOperationParam;
import com.xiaoyuan.common.vo.R;

/**
 * FileName:    UserOperationService
 * Author:      小袁教程
 * Date:        2022/6/7 12:13
 * Description:
 */
public interface UserOperationService extends IService<UserOperation> {

    R selectOperationList(UserOperationParam operationParam);

    R selectOperationType();
}
