package com.xiaoyuan.model.vo;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

/**
 * FileName:    FriendLinkVo
 * Author:      小袁
 * Date:        2022/4/30 15:04
 * Description:
 */
@Data
public class FriendLinkVo {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 简单描述
     */
    private String describe;

    /**
     * 链接
     */
    private String link;
}
