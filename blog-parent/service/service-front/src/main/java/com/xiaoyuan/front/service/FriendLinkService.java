package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.db.entity.FriendLink;
import com.xiaoyuan.utils.vo.R;

/**
 * FileName:    FriendLinkService
 * Author:      小袁
 * Date:        2022/4/30 15:10
 * Description:
 */
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 查询所有友链
     * @return
     */
    R findFriendLinkAll();
}
