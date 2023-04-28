package com.xiaoyuan.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyuan.common.pojo.HomeMessage;
import com.xiaoyuan.common.vo.R;

import java.util.List;

/**
 * FileName:    HomeMessageService
 * Author:      小袁
 * Date:        2022/5/1 0:23
 * Description:
 */
public interface HomeMessageService extends IService<HomeMessage> {

    /**
     * 单个删除
     * @param id
     * @return
     */
    R delete(String id);

    /**
     * 多次删除
     * @param ids
     * @return
     */
    R deleteMore(List<String> ids);

    /**
     * 编辑
     * @param homeMessage
     * @return
     */
    R modify(HomeMessage homeMessage);

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    R listHomeMessagePage(Integer pageIndex, Integer pageSize);
}
