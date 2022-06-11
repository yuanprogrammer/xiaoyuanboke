package com.xiaoyuan.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.UserOperationMapper;
import com.xiaoyuan.back.db.entity.UserOperation;
import com.xiaoyuan.back.service.UserOperationService;
import com.xiaoyuan.back.vo.param.UserOperationParam;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * FileName:    UserOperationServiceImpl
 * Author:      小袁教程
 * Date:        2022/6/7 12:13
 * Description:
 */
@Service
public class UserOperationServiceImpl extends ServiceImpl<UserOperationMapper, UserOperation> implements UserOperationService {
    @Override
    public R selectOperationList(PageUtils pageUtils, UserOperationParam operationParam) {
        if (pageUtils == null) return R.error(HttpStatusEnum.PARAM_ERROR);

        int index = pageUtils.getPageIndex();
        int size = pageUtils.getPageSize();
        int start = (index - 1) * size;

        if (index < 1 || size < 1) {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }else if (size > 50) {
            return R.error(HttpStatusEnum.PARAM_LENGTH_BEYOND);
        }

        HashMap<String, Object> map = new HashMap<>();
        if (operationParam != null) {
            map = JSONObject.parseObject(JSON.toJSONString(operationParam), new TypeReference<HashMap<String, Object>>(){});
        }

        map.put("start", start);
        map.put("size", size);

        return R.ok().data("list", this.baseMapper.selectUserOperationList(map)).data("total", this.baseMapper.findTotal(map));
    }

    @Override
    public R selectOperationType() {
        return R.ok().data("operationType", this.baseMapper.selectOperationType());
    }
}
