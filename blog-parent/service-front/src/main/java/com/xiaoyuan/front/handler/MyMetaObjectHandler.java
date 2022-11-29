package com.xiaoyuan.front.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * FileName:    MyMataObjectHandler
 * Author:      小袁
 * Date:        2022/4/16 11:30
 * Description:
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 配置初始创建时间
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        // 初始状态为0
        this.setFieldValByName("problemState", '0', metaObject);
        this.setFieldValByName("noticeState", '0', metaObject);
        // 随机背景图
        this.setFieldValByName("background", "https://article.xiaoyuan-boke.com/background/m_bg" + (new Random().nextInt(8) + 1)+  ".jpg", metaObject);
        // 默认头像
        this.setFieldValByName("avatar", "https://user.xiaoyuan-boke.com/user/avatar/user_default.png", metaObject);
        // 初始名字
        this.setFieldValByName("nickname", "未设置昵称", metaObject);
    }

    /**
     * 修改数据执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动更改修改时间
        this.setFieldValByName("gmtUpdate", new Date(), metaObject);
    }
}
