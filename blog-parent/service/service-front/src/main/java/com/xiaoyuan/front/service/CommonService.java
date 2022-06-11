package com.xiaoyuan.front.service;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.front.db.dao.CommonUserMapper;
import com.xiaoyuan.front.utils.StringThreadLocal;
import com.xiaoyuan.front.vo.param.LoginParam;
import com.xiaoyuan.tencent.service.SmsService;
import com.xiaoyuan.utils.StringUtil;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.constant.RedisConstantKey;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * FileName:    CommonService
 * Author:      小袁
 * Date:        2022/5/2 11:51
 * Description:
 */
@Service
public class CommonService {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public R sendEmailCode(String permissionCode, String emailJson) {
        if (StringUtils.isBlank(permissionCode) || StringUtils.isBlank(emailJson)) return R.error(HttpStatusEnum.PARAM_ERROR);

        String email = JSON.parseObject(emailJson).getString("email").trim();
        if (StringUtils.isBlank(email) || !StringUtil.checkEmail(email)) {
            return R.error(HttpStatusEnum.EMAIL_ERROR);
        }else {
            // 校验权限码
            String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.EMAIL_REQUEST_VERIFY + email);
            if (StringUtils.isBlank(rightCode) || !permissionCode.equals(rightCode)) {
                return R.error(HttpStatusEnum.ILLEGAL_OPERATION);
            }
        }

        // 随机生成验证码
        String code = String.valueOf(new Random().nextInt(899999) + 100000);

        // 正文内容
        String content = "亲爱的用户：\n" +
                "您此次的验证码为：\n\n" +
                code + "\n\n" +
                "此验证码5分钟内有效，请立即进行下一步操作。 如非你本人操作，请忽略此邮件。\n" +
                "感谢您的使用！";
        // 发送验证码
        threadService.sendSimpleMail(email, "您此次的验证码为：" + code, content);
        // 丢入缓存，设置5分钟过期
        redisTemplate.opsForValue().set(RedisConstantKey.EMAIL + email, code, RedisConstantKey.EMAIL_EXPIRE, TimeUnit.SECONDS);
        return R.ok();
    }

    @Autowired
    private CommonUserMapper commonUserMapper;

    /**
     * 发送短信验证码
     * @param permissionCode
     * @param phoneJson
     * @return
     */
    public R sendSmsCode(String permissionCode, String phoneJson) {
        if (StringUtils.isBlank(phoneJson)) return R.error(HttpStatusEnum.PARAM_ERROR);

        String phone = JSON.parseObject(phoneJson).get("phone").toString().trim();
        // 合法性校验
        if (!StringUtil.checkPhone(phone)) {
            return R.error(HttpStatusEnum.MOBILE_NUMBER_ERROR);
        }else {
            String rightCode = redisTemplate.opsForValue().get(RedisConstantKey.MOBILE_NUMBER_REQUEST_VERIFY + phone);
            if (StringUtils.isBlank(rightCode)) {
                return R.error(HttpStatusEnum.ILLEGAL_OPERATION);
            }else if (!rightCode.equals(permissionCode)) {
                return R.error(HttpStatusEnum.ILLEGAL_OPERATION);
            }
        }

        return smsService.sendSmsCode(phone);
    }

    // 获取短信验证码权限Cookie
    public R getRequestPermissionCode(String phoneJson) {
        if (phoneJson == null) {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }
        String phone1 = JSON.parseObject(phoneJson).get("phone").toString().trim();
        if (!StringUtil.checkPhone(phone1)) {
            return R.error(HttpStatusEnum.PARAM_ERROR);
        }

        String permissionCode = UUID.randomUUID().toString();
        StringThreadLocal.put(permissionCode);
        redisTemplate.opsForValue().set(RedisConstantKey.MOBILE_NUMBER_REQUEST_VERIFY + phone1, permissionCode , RedisConstantKey.EXPIRE_TEN_SECOND, TimeUnit.SECONDS);
        return R.ok();
    }

    // 获取发送邮箱权限码
    public R getEMailCodePermission(String emailJson) {
        if (StringUtils.isBlank(emailJson)) return R.error(HttpStatusEnum.PARAM_ERROR);

        String email = JSON.parseObject(emailJson).getString("email").trim();
        if (StringUtils.isBlank(email) || !StringUtil.checkEmail(email)) {
            return R.error(HttpStatusEnum.EMAIL_ERROR);
        }

        // 随机生成权限码
        String permissionCode = UUID.randomUUID().toString();

        StringThreadLocal.put(permissionCode);
        redisTemplate.opsForValue().set(RedisConstantKey.EMAIL_REQUEST_VERIFY + email, permissionCode, RedisConstantKey.EXPIRE_TEN_SECOND, TimeUnit.SECONDS);
        return R.ok();
    }
}
