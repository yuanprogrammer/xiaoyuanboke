package com.xiaoyuan.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.front.vo.param.HomeMessageParam;
import com.xiaoyuan.common.pojo.HomeMessage;
import com.xiaoyuan.common.vo.PageUtils;
import com.xiaoyuan.common.vo.R;

/**
 * FileName:    HomeMessageService
 * Author:      小袁
 * Date:        2022/4/27 20:16
 * Description:
 */
public interface HomeMessageService extends IService<HomeMessage> {

    /**
     * 插入一条留言
     * @param homeMessageParam
     * @return
     */
    R insert(HomeMessageParam homeMessageParam);

    /**
     * 查询首页留言
     * @param pageUtils 分页对象
     * @return
     */
    R listHomeMessagePage(PageUtils pageUtils);

    /**
     * 查询留言总数
     * @return
     */
    Integer findMessageTotal();
}
