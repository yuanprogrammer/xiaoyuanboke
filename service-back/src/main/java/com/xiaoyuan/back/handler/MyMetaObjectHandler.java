package com.xiaoyuan.back.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        // 点赞量初始0
        this.setFieldValByName("goodCount", 0, metaObject);
        // 配置初始文章浏览量为 0
        this.setFieldValByName("viewCount", 0, metaObject);
        // 配置初始文章评论数量为 0
        this.setFieldValByName("commentCount", 0, metaObject);
        // 逻辑删除初始0
        this.setFieldValByName("deleted", '0', metaObject);
        // 配置初始创建时间
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        // 配置初始修改时间
        this.setFieldValByName("gmtUpdate", new Date(), metaObject);
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
