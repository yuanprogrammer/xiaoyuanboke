package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.FriendLinkMapper;
import com.xiaoyuan.back.service.FriendLinkService;
import com.xiaoyuan.common.pojo.FriendLink;
import com.xiaoyuan.common.vo.PageUtils;
import com.xiaoyuan.common.vo.R;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * FileName:    FriendLinkServiceImpl
 * Author:      小袁
 * Date:        2022/4/30 15:10
 * Description:
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Override
    public R insert(FriendLink friendLink) {
        return this.baseMapper.insert(friendLink) == 0 ? R.fail("添加失败") : R.success();
    }

    @Override
    public R deleteById(Integer id) {
        return this.baseMapper.deleteById(id) == 0 ? R.fail("删除失败") : R.success();
    }

    @Override
    public R modifyById(FriendLink friendLink) {
        return this.baseMapper.updateById(friendLink) == 0 ? R.fail("修改失败") : R.success();
    }

    @Override
    public R listFriendLinkPage(Integer pageIndex, Integer pageSize) {
        Page<FriendLink> page = new Page<>(pageIndex, pageSize);
        IPage<FriendLink> iPage = this.baseMapper.selectPage(page, null);

        PageUtils pageUtils = new PageUtils(iPage.getRecords(), iPage.getTotal(), pageIndex, pageSize);

        HashMap<String, Object> map = new HashMap<>();
        map.put("friendLinkList", pageUtils);
        return R.success(map);
    }
}
