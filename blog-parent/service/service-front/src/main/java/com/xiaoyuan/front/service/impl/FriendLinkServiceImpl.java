package com.xiaoyuan.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.front.db.dao.FriendLinkMapper;
import com.xiaoyuan.front.db.entity.FriendLink;
import com.xiaoyuan.front.service.FriendLinkService;
import com.xiaoyuan.utils.vo.R;
import org.springframework.stereotype.Service;

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

        return R.ok().data("friendLinkList", this.baseMapper.selectList(null));
    }
}
