package com.xiaoyuan.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.UserOperationMapper;
import com.xiaoyuan.back.service.UserOperationService;
import com.xiaoyuan.common.pojo.UserOperation;
import com.xiaoyuan.common.param.UserOperationParam;
import com.xiaoyuan.common.enums.HttpStatusEnum;
import com.xiaoyuan.common.vo.R;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName:    UserOperationServiceImpl
 * Author:      小袁教程
 * Date:        2022/6/7 12:13
 * Description:
 */
@Service
public class UserOperationServiceImpl extends ServiceImpl<UserOperationMapper, UserOperation> implements UserOperationService {
    @Override
    public R selectOperationList(UserOperationParam operationParam) {

        int index = operationParam.getPageIndex();
        int size = operationParam.getPageSize();
        int start = (index - 1) * size;

        if (index < 1 || size < 1) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (size > 50) {
            return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
        }

        HashMap<String, Object> map = new HashMap<>();
        if (operationParam != null) {
            map = JSONObject.parseObject(JSON.toJSONString(operationParam), new TypeReference<HashMap<String, Object>>(){});
        }

        map.put("start", start);
        map.put("size", size);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("list", this.baseMapper.selectUserOperationList(map));
        resMap.put("total", this.baseMapper.findTotal(map));
        return R.success(resMap);
    }

    @Override
    public R selectOperationType() {
        Map<String, Object> map = new HashMap<>();
        map.put("operationType", this.baseMapper.selectOperationType());
        return R.success(map);
    }
}
