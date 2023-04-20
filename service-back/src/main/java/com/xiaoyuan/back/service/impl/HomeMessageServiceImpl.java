package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.mapper.HomeMessageMapper;
import com.xiaoyuan.back.service.HomeMessageService;
import com.xiaoyuan.model.entity.HomeMessage;
import com.xiaoyuan.model.vo.HomeMessageVo;
import com.xiaoyuan.model.vo.PageUtils;
import com.xiaoyuan.model.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FileName:    HomeMessageServiceImpl
 * Author:      小袁
 * Date:        2022/5/1 0:23
 * Description:
 */
@Service
@Transactional
public class HomeMessageServiceImpl extends ServiceImpl<HomeMessageMapper, HomeMessage> implements HomeMessageService {

    @Override
    public R delete(String id) {
        return this.baseMapper.deleteById(id) == 0 ? R.fail("删除失败") : R.success();
    }

    @Override
    public R deleteMore(List<String> ids) {
        return this.baseMapper.deleteBatchIds(ids) == 0 ? R.fail("删除失败") : R.success();
    }

    @Override
    public R modify(HomeMessage homeMessage) {
        return this.baseMapper.updateById(homeMessage) == 0 ? R.fail("修改失败") : R.success();
    }

    @Override
    public R listHomeMessagePage(Integer pageIndex, Integer pageSize) {
        Page<HomeMessage> page = new Page<>(pageIndex, pageSize);
        IPage<HomeMessage> iPage = this.baseMapper.selectPage(page, null);

        List<HomeMessageVo> messageVoList = new ArrayList<>();
        iPage.getRecords().forEach(item -> {
            messageVoList.add(copy(item));
        });

        PageUtils pageUtils = new PageUtils(messageVoList, iPage.getTotal(), pageIndex, pageSize);

        HashMap<String, Object> map = new HashMap<>();
        map.put("homeMessageList", pageUtils);
        return R.success(map);
    }

    private HomeMessageVo copy(HomeMessage homeMessage) {
        HomeMessageVo homeMessageVo = new HomeMessageVo();
        homeMessageVo.setId(String.valueOf(homeMessage.getId()));
        homeMessageVo.setAuthorId(String.valueOf(homeMessage.getAuthorId()));
        BeanUtils.copyProperties(homeMessage, homeMessageVo);

        return homeMessageVo;
    }
}
