package com.xiaoyuan.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.back.db.dao.HomeMessageMapper;
import com.xiaoyuan.back.db.entity.HomeMessage;
import com.xiaoyuan.back.service.HomeMessageService;
import com.xiaoyuan.back.vo.HomeMessageVo;
import com.xiaoyuan.utils.vo.PageUtils;
import com.xiaoyuan.utils.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        return this.baseMapper.deleteById(id) == 0 ? R.error().message("删除失败") : R.ok();
    }

    @Override
    public R deleteMore(List<String> ids) {
        return this.baseMapper.deleteBatchIds(ids) == 0 ? R.error().message("删除失败") : R.ok();
    }

    @Override
    public R modify(HomeMessage homeMessage) {
        return this.baseMapper.updateById(homeMessage) == 0 ? R.error().message("修改失败") : R.ok();
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
        return R.ok().data("homeMessageList", pageUtils);
    }

    private HomeMessageVo copy(HomeMessage homeMessage) {
        HomeMessageVo homeMessageVo = new HomeMessageVo();
        homeMessageVo.setId(String.valueOf(homeMessage.getId()));
        homeMessageVo.setAuthorId(String.valueOf(homeMessage.getAuthorId()));
        BeanUtils.copyProperties(homeMessage, homeMessageVo);

        return homeMessageVo;
    }
}
