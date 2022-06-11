package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.back.db.entity.UserOperation;
import com.xiaoyuan.back.vo.param.UserOperationParam;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    UserOperationService
 * Author:      小袁教程
 * Date:        2022/6/7 12:13
 * Description:
 */
public interface UserOperationService extends IService<UserOperation> {

    R selectOperationList(PageUtils pageUtils, UserOperationParam operationParam);

    R selectOperationType();
}
