package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.mapper.FriendLinkMapper;
import com.xiaoyuan.front.service.FriendLinkService;
import com.xiaoyuan.common.pojo.FriendLink;
import com.xiaoyuan.common.vo.R;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName:    FriendLinkServiceImpl
 * Author:      小袁
 * Date:        2022/4/30 15:10
 * Description:
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Override
    public R findFriendLinkAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("friendLinkList", this.baseMapper.selectList(null));

        return R.success(map);
    }
}
