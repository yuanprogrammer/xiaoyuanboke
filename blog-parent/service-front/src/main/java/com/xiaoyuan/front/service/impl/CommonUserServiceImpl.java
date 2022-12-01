package com.xiaoyuan.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyuan.common_util.auth.EncryptionAlgorithmUtil;
import com.xiaoyuan.common_util.auth.JWTUtil;
import com.xiaoyuan.common_util.match.StringMatch;
import com.xiaoyuan.front.mapper.CommonUserMapper;
import com.xiaoyuan.front.service.CommonUserService;
import com.xiaoyuan.front.service.ThreadService;
import com.xiaoyuan.front.service.TokenService;
import com.xiaoyuan.front.utils.StringThreadLocal;
import com.xiaoyuan.front.utils.UserThreadLocal;
import com.xiaoyuan.front.vo.CommonUserVo;
import com.xiaoyuan.front.vo.param.CodeParam;
import com.xiaoyuan.front.vo.param.LoginParam;
import com.xiaoyuan.model.constants.CookieConstant;
import com.xiaoyuan.model.constants.RedisConstantKey;
import com.xiaoyuan.model.entity.CommonUser;
import com.xiaoyuan.model.enums.HttpStatusEnum;
import com.xiaoyuan.model.vo.R;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * FileName:    CommonUserServiceImpl
 * Author:      小袁
 * Date:        2022/5/1 22:57
 * Description:
 */
@Service
@Transactional
public class CommonUserServiceImpl extends ServiceImpl<CommonUserMapper, CommonUser> implements CommonUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ThreadService threadService;

    @Override
    public R login(LoginParam loginParam) {
        if (loginParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();

        // 参数非法校验
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (StringMatch.isContainsEmoji(account) || StringMatch.isContainsChinese(account)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (StringMatch.isContainsEmoji(password) || StringMatch.isContainsChinese(password)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (account.length() > 25 || password.length() > 20) {
            return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
        }

        // 获取加密盐
        String salt = this.baseMapper.getUserSalt(account);
        if (StringUtils.isBlank(salt)) {
            // 用户不存在
            return R.fail(HttpStatusEnum.USER_NOT_EXIST);
        }

        // 用户存在, 校验密码
        CommonUser commonUser = this.baseMapper.isPasswordCorrect(account, DigestUtils.md5Hex(password + salt));
        if (commonUser == null) {
            return R.fail(HttpStatusEnum.PASSWORD_ERROR);
        }

        // 更新登录时间
        commonUser.setGmtLogin(new Date());
        this.baseMapper.updateById(commonUser);

        // 记录用户操作
        threadService.recordUserOperation(commonUser.getId(), "用户登录");

        // 获取用户信息
        commonUser = this.baseMapper.getUserInfoById(commonUser.getId());
        // 用户数据进一步处理
        CommonUserVo commonUserVo = new CommonUserVo();
        commonUserVo.setNumber(EncryptionAlgorithmUtil.encrypt(commonUser.getId().toString())); // 编号加密
        BeanUtils.copyProperties(commonUser, commonUserVo);
        // 验证全部通过, 生成token作为key
        String token = JWTUtil.createToken(commonUser.getId());
        // 写入本地线程, 存入cookie
        StringThreadLocal.put(token);
        // 用户数据丢入redis, 设置1天过期时间
        redisTemplate.opsForValue().set(RedisConstantKey.USER + token, JSON.toJSONString(commonUserVo), RedisConstantKey.USER_EXPIRE, TimeUnit.SECONDS);
        return R.success();
    }

    @Override
    public R register(LoginParam loginParam) {
        if (loginParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String passwordConfirm = loginParam.getPasswordConfirm();
        String select = loginParam.getSelect();
        String code = loginParam.getCode();

        // 参数空校验
        if (StringUtils.isAnyBlank(account, password, passwordConfirm, select)) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }

        // 参数非法校验
        if (StringMatch.isContainsEmoji(account) || StringMatch.isContainsChinese(account)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (StringMatch.isContainsEmoji(password) || StringMatch.isContainsChinese(password)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (StringMatch.isContainsEmoji(passwordConfirm) || StringMatch.isContainsEmoji(passwordConfirm)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (password.length() < 6 || password.length() > 20) { // 长度校验
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }

        // 二次密码校验
        if (!password.equals(passwordConfirm)) {
            return R.fail(HttpStatusEnum.PASSWORD_INCONSISTENT);
        }

        // 判断用户是否存在
        String userSalt = this.baseMapper.getUserSalt(account);
        if (!StringUtils.isBlank(userSalt)) {
            return R.fail(HttpStatusEnum.USER_ALREADY_EXIST);
        }

        // 注册
        CommonUser commonUser = new CommonUser();
        switch (select) {
            case "1":
                if (account.length() > 25) {
                    return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
                }
                if (!StringMatch.checkUsername(account)) {
                    return R.fail(HttpStatusEnum.PARAM_ERROR);
                }
                commonUser.setUsername(account);
                break;
            case "2":
                // 校验邮箱合法性
                if (account.length() > 20) {
                    return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
                }
                if (!StringMatch.isEmail(account)) {
                    return R.fail(HttpStatusEnum.EMAIL_ERROR);
                }
                // 校验验证码是否正确
                if (StringUtils.isBlank(code) || code.length() != 6) {
                    return R.fail(HttpStatusEnum.PARAM_ERROR);
                }
                if (!redisTemplate.hasKey(account)) {
                    return R.fail(HttpStatusEnum.CODE_INVALID); // 失效
                }else {
                    if (!code.equals(redisTemplate.opsForValue().get(RedisConstantKey.EMAIL + account))) {
                        return R.fail(HttpStatusEnum.CODE_ERROR); // 不正确
                    }
                }
                // 清除验证码
                commonUser.setEmail(account);
                break;
            case "3":
                if (account.length() != 11) {
                    return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
                }else if (!StringMatch.isPhone(account)) {
                    return R.fail(HttpStatusEnum.MOBILE_NUMBER_ERROR);
                }
                // 短信验证码校验
                if (StringUtils.isBlank(code) || code.length() != 6) {
                    return R.fail(HttpStatusEnum.PARAM_ERROR);
                }else {
                    String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.MOBILE_NUMBER + account);
                    if (StringUtils.isBlank(rightCode)) {
                        return R.fail(HttpStatusEnum.CODE_INVALID);
                    }else if (!code.equals(rightCode)) {
                        return R.fail(HttpStatusEnum.CODE_ERROR);
                    }
                }
                commonUser.setMobileNumber(account);
                break;
            default:
                return R.fail(HttpStatusEnum.PARAM_ERROR);
        }

        // 清空验证码
        if ("2".equals(select)) {
            redisTemplate.delete(RedisConstantKey.EMAIL + account);
        }else if ("3".equals(select)) {
            redisTemplate.delete(RedisConstantKey.MOBILE_NUMBER + account);
        }

        // 随机生成加密盐
        String salt = RandomStringUtils.randomAlphabetic(4) + "#!$@";
        // 加密密码
        password = DigestUtils.md5Hex(password + salt);
        // 赋值数据
        commonUser.setSalt(salt);
        commonUser.setPassword(password);
        commonUser.setComUserCode("comUser::" + RandomUtil.randomStrUUID(true));
        // 插入数据库
        int result = this.baseMapper.insert(commonUser);
        if (result == 0) {
            return R.fail().message("注册失败");
        }else {
            // 记录用户操作
            threadService.recordUserOperation(commonUser.getId(), "用户登录");

            CommonUserVo commonUserVo = new CommonUserVo();
            commonUserVo.setNumber(EncryptionAlgorithmUtil.encrypt(commonUser.getId().toString())); // 编号加密
            BeanUtils.copyProperties(commonUser, commonUserVo);
            // 设置token
            String token = JWTUtil.createToken(commonUser.getId());
            // 写入本地线程, 存入Cookie
            StringThreadLocal.put(token);
            // 存入redis
            redisTemplate.opsForValue().set(RedisConstantKey.USER + token, JSON.toJSONString(commonUserVo), RedisConstantKey.USER_EXPIRE, TimeUnit.SECONDS);
            return R.success();
        }
    }

    @Override
    public R modifyPassword(LoginParam loginParam) {
        if (loginParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);

        // 校验密码合法性
        String password = loginParam.getPassword();
        String passwordConfirm = loginParam.getPasswordConfirm();
        if (StringUtils.isAnyBlank(password, passwordConfirm)) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (StringMatch.isContainsEmoji(password) || StringMatch.isContainsEmoji(passwordConfirm)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL); // 检测表情符号
        }else if (StringMatch.isContainsChinese(password) || StringMatch.isContainsChinese(passwordConfirm)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL); // 检测中文
        }else if (password.length() < 6 || password.length() > 20 || passwordConfirm.length() < 6 || passwordConfirm.length() > 20) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }

        // 获取被权限拦截的用户信息
        CommonUserVo commonUserVo = UserThreadLocal.get();
        if (commonUserVo == null) return R.fail(HttpStatusEnum.TOKEN_INVALID);

        // 获取用户加密盐
        QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
        wrapper.select("salt");
        wrapper.eq("id", EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        // 只需要查询一条，限制一下，避免全表扫描
        wrapper.last("limit 1");
        CommonUser commonUser = this.baseMapper.selectOne(wrapper);

        // 判断用户密码是否正确
        wrapper.clear(); // 先清空条件
        wrapper.select("id", "salt");
        wrapper.eq("id", EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        wrapper.eq("password", DigestUtils.md5Hex(password + commonUser.getSalt()));
        wrapper.last("limit 1");
        commonUser = this.baseMapper.selectOne(wrapper);

        // 校验密码
        if (commonUser == null) {
            // 密码错误
            return R.fail(HttpStatusEnum.PASSWORD_ERROR);
        }
        else {
            // 记录
            threadService.recordUserOperation(commonUser.getId(), "修改密码");

            // 覆盖密码
            commonUser.setPassword(DigestUtils.md5Hex(passwordConfirm + commonUser.getSalt()));
            return this.baseMapper.updateById(commonUser) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
        }
    }

    @Override
    public R modifyNickname(String token, CommonUserVo commonUserVo) {
        // 参数校验
        if (commonUserVo == null) return R.fail(HttpStatusEnum.PARAM_ERROR);
        String nickname = commonUserVo.getNickname().replaceAll("\\s*", "");
        String username = commonUserVo.getUsername();

        // 校验昵称合法性
        if (StringUtils.isBlank(nickname)) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (StringMatch.isContainsEmoji(nickname)) {
            return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
        }else if (nickname.length() > 30) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }

        // 校验 用户名合法性
        if (!StringUtils.isBlank(username)) {
            if (StringMatch.isContainsEmoji(username)) {
                return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
            }else if (StringMatch.isContainsChinese(username)) {
                return R.fail(HttpStatusEnum.PARAM_ILLEGAL);
            }else if (username.length() < 5 || username.length() > 25) {
                return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
            }
        }

        CommonUser commonUser = new CommonUser();
        // 判断是否有修改用户名
        commonUser.setNickname(nickname);
        if (!StringUtils.isBlank(username)) {
            // 判断用户名是否存在
            QueryWrapper<CommonUser> wrapper = new QueryWrapper<>();
            wrapper.select("salt");
            wrapper.eq("username", username);
            wrapper.last("limit 1");
            if (this.baseMapper.selectOne(wrapper) != null) {
                return R.fail(HttpStatusEnum.USER_ALREADY_EXIST);
            }else {
                commonUser.setUsername(username);
            }
        }

        // 获取被权限校验拦截的用户信息
        commonUserVo = UserThreadLocal.get();
        if (commonUserVo == null) return R.fail(HttpStatusEnum.TOKEN_INVALID);

        // 记录
        Long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        threadService.recordUserOperation(userId, "修改昵称或者用户名");

        // 全部验证通过, 修改信息
        commonUser.setId(userId);
        // 更新redis用户数据
        commonUserVo.setNickname(nickname);
        if (!StringUtils.isBlank(username))
            commonUserVo.setUsername(username);
        Long expire = redisTemplate.getExpire(RedisConstantKey.USER + token);
        if (expire != null) {
            redisTemplate.opsForValue().set(RedisConstantKey.USER + token, JSON.toJSONString(commonUserVo), expire, TimeUnit.SECONDS);
        }
        // 更新数据库
        return this.baseMapper.updateById(commonUser) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R findPassword(String token, LoginParam loginParam) {
        if (loginParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String code = loginParam.getCode();

        if (StringUtils.isAnyBlank(account, password, code, token) || code.length() != 6) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (password.length() < 6 || password.length() > 20) {
            return R.fail(HttpStatusEnum.PARAM_LENGTH_BEYOND);
        }

        CommonUserVo commonUserVo = tokenService.checkToken(token);
        if (commonUserVo == null) return R.fail(HttpStatusEnum.USER_NO_LOGIN);

        CommonUser commonUser = new CommonUser();
        long id = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        commonUser.setId(id);

        if (StringMatch.isEmail(account)) {
            // 邮箱验证
            String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.EMAIL + account);
            if (StringUtils.isAnyBlank(rightCode)) {
                return R.fail(HttpStatusEnum.CODE_ERROR); // redis中不存在
            }else if (!code.equals(rightCode)) {
                return R.fail(HttpStatusEnum.CODE_ERROR); // 不一致
            }

            // 验证通过，清除验证码
            redisTemplate.delete(RedisConstantKey.EMAIL + account);
        }else if (StringMatch.isPhone(account)) {
            // 号码验证
            String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.MOBILE_NUMBER + account);
            if (StringUtils.isAnyBlank(rightCode)) {
                return R.fail(HttpStatusEnum.CODE_ERROR);
            }else if (!code.equals(rightCode)) {
                return R.fail(HttpStatusEnum.CODE_ERROR);
            }

            // 验证通过，清除验证码
            redisTemplate.delete(RedisConstantKey.MOBILE_NUMBER + account);
        }else {
            return R.fail(HttpStatusEnum.ILLEGAL_OPERATION);
        }

        // 记录
        threadService.recordUserOperation(commonUser.getId(), "找回密码");

        // 新密码覆盖
        String salt = this.baseMapper.getUserSaltById(id);
        commonUser.setPassword(DigestUtils.md5Hex(password + salt));
        return this.baseMapper.updateById(commonUser) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R modifyEmail(String token, CodeParam codeParam) {
        if (codeParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);

        String email = codeParam.getEmail();
        String code = codeParam.getCode();

        if (StringUtils.isAnyBlank(token, email, code) || code.length() != 6) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (StringMatch.isContainsEmoji(email) || StringMatch.isContainsChinese(email)) {
            return R.fail(HttpStatusEnum.EMAIL_ERROR);
        }

        CommonUserVo commonUserVo = tokenService.checkToken(token);
        // 验证token
        if (commonUserVo == null) {
            return R.fail(HttpStatusEnum.TOKEN_INVALID);
        }

        // 邮箱是否已经被注册
        if (this.baseMapper.isContainsEmail(email)) {
            return R.fail(HttpStatusEnum.EMAIL_ALREADY_EXIST);
        }

        // 核对验证码
        String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.EMAIL + email);
        if (StringUtils.isBlank(rightCode)) {
            return R.fail(HttpStatusEnum.CODE_INVALID);
        }else if (!code.equals(rightCode)) {
            return R.fail(HttpStatusEnum.CODE_ERROR);
        }

        Long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        threadService.recordUserOperation(userId, "修改邮箱");

        // 删除验证码
        redisTemplate.delete(RedisConstantKey.EMAIL + email);

        CommonUser commonUser = new CommonUser();
        commonUser.setId(userId);
        commonUser.setEmail(email);
        // 更新redis用户信息
        Long expire = redisTemplate.getExpire(RedisConstantKey.USER + token);
        commonUserVo.setEmail(email);
        if (expire != null && expire > 0) {
            redisTemplate.opsForValue().set(RedisConstantKey.USER + token, JSON.toJSONString(commonUserVo), expire, TimeUnit.SECONDS);
        }
        // 插入
        return this.baseMapper.updateById(commonUser) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R modifyMobileNumber(String token, CodeParam codeParam) {
        if (codeParam == null) return R.fail(HttpStatusEnum.PARAM_ERROR);

        String mobileNumber = codeParam.getMobileNumber();
        String code = codeParam.getCode();
        // 参数合法性校验
        if (StringUtils.isBlank(token) || StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(code)) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (mobileNumber.length() != 11 || code.length() != 6) {
            return R.fail(HttpStatusEnum.PARAM_ERROR);
        }else if (!StringMatch.isPhone(mobileNumber)) {
            return R.fail(HttpStatusEnum.MOBILE_NUMBER_ERROR);
        }else if (this.baseMapper.isContainsMobileNumber(mobileNumber)) {
            // 号码已被注册
            return R.fail(HttpStatusEnum.MOBILE_NUMBER_ALREADY_EXIST);
        }

        CommonUserVo commonUserVo = tokenService.checkToken(token);
        String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.MOBILE_NUMBER + mobileNumber);
        if (commonUserVo == null) {
            // 没有登录信息
            return R.fail(HttpStatusEnum.TOKEN_INVALID);
        }else if (StringUtils.isBlank(rightCode) || !code.equals(rightCode)) {
            // 校验验证码是否正确
            return R.fail(HttpStatusEnum.CODE_ERROR);
        }

        Long userId = Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber()));
        threadService.recordUserOperation(userId, "修改号码");

        // 更新redis
        commonUserVo.setMobileNumber(mobileNumber);
        Long expire = redisTemplate.getExpire(CookieConstant.TOKEN + token);
        if (expire != null && expire > 0) {
            redisTemplate.opsForValue().set(CookieConstant.TOKEN + token, JSON.toJSONString(commonUserVo), expire, TimeUnit.SECONDS);
        }

        // 删除验证码
        redisTemplate.delete(RedisConstantKey.MOBILE_NUMBER + mobileNumber);

        // 验证全部通过, 插入数据
        CommonUser commonUser = new CommonUser();
        commonUser.setId(userId);
        commonUser.setMobileNumber(mobileNumber);
        return this.baseMapper.updateById(commonUser) == 0 ? R.fail(HttpStatusEnum.UNKNOWN_ERROR) : R.success();
    }

    @Override
    public R getUserInfo(String token) {
        // 验证token
        CommonUserVo commonUserVo = tokenService.checkToken(token);

        if (commonUserVo == null) {
            return R.success();
        }else {
            // 刷新缓存时间
            redisTemplate.opsForValue().set(RedisConstantKey.USER + token, JSON.toJSONString(commonUserVo), RedisConstantKey.USER_EXPIRE, TimeUnit.SECONDS);
            commonUserVo.setNumber("");

            Map<String, Object> map = new HashMap<>();
            map.put("userInfo", commonUserVo);

            return R.success(map);
        }
    }

    @Override
    public R logout(String token) {
        CommonUserVo commonUserVo = tokenService.checkToken(token);
        redisTemplate.delete(RedisConstantKey.USER + token);
        if (commonUserVo != null) {
            threadService.recordUserOperation(Long.parseLong(EncryptionAlgorithmUtil.decode(commonUserVo.getNumber())), "退出登录");
        }
        return R.success();
    }
}
