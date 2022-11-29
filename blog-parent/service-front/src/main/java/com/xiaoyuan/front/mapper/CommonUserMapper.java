package com.xiaoyuan.front.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyuan.model.entity.CommonUser;
import org.springframework.stereotype.Repository;

/**
 * FileName:    CommonUserMapper
 * Author:      小袁
 * Date:        2022/4/17 17:25
 * Description: 普通用户DAO
 */
@Repository
public interface CommonUserMapper extends BaseMapper<CommonUser> {

    CommonUser getUserInfoById(Long id);

    /**
     * 获取加密盐
     * @param account
     * @return
     */
    String getUserSalt(String account);

    /**
     * 验证密码是否正确
     * @param account
     * @param password
     * @return
     */
    CommonUser isPasswordCorrect(String account, String password);

    /**
     * 验证邮箱是否已经被注册
     * @param email
     * @return
     */
    boolean isContainsEmail(String email);

    /**
     * 验证号码是否已经被注册
     * @param mobileNumber
     * @return
     */
    boolean isContainsMobileNumber(String mobileNumber);

    /**
     * 根据ID获取加密盐
     * @param id
     * @return
     */
    String getUserSaltById(long id);
}
