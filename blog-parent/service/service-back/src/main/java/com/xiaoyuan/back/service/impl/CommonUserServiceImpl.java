package com.xiaoyuan.back.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.CommonUserMapper;
import com.xiaoyuan.back.db.entity.CommonUser;
import com.xiaoyuan.back.service.CommonUserService;
import com.xiaoyuan.back.vo.CommonUserVo;
import com.xiaoyuan.back.vo.param.UserQueryParam;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * FileName:    CommonUserServiceImpl
 * Author:      小袁
 * Date:        2022/4/17 17:29
 * Description:
 */
@Repository
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser> implements CommonUserService {

    @Autowired
    private CommonUserMapper commonUserMapper;

    @Override
    public CommonUserVo getCommonUserInfoById(Long userId) {
        QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
        wrapper.select("nickname", "avatar");
        wrapper.last("limit 1");

        return copy(commonUserMapper.selectList(wrapper).get(0));
    }

    @Override
    public R selectUserList(PageUtils pageUtils, UserQueryParam queryParam) {
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
        if (queryParam != null) {
            map = JSONObject.parseObject(JSON.toJSONString(queryParam), new TypeReference<HashMap<String, Object>>(){});
        }

        map.put("start", start);
        map.put("size", size);

        return R.ok().data("userList", this.baseMapper.selectUserList(map)).data("total", this.baseMapper.findTotal());
    }


    private CommonUserVo copy(CommonUser commonUser) {
        CommonUserVo commonUserVo = new CommonUserVo();
        BeanUtils.copyProperties(commonUser, commonUserVo);

        return commonUserVo;
    }
}
