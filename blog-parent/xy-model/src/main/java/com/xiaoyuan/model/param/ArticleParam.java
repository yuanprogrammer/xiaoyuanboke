package com.xiaoyuan.model.param;

import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoyuan.common_util.generate.RandomUtil;
import com.xiaoyuan.model.comm.MessageParam;
import com.xiaoyuan.model.common.BaseMessage;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * FileName:    ArticleParam
 * Author:      小袁
 * Date:        2022/4/16 11:37
 * Description:
 */
@Data
public class ArticleParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章编号
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 文章 markdown文本和html格式文本
     */
    private ArticleContentParam articleContentParam;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 分类专栏
     */
    private List<Integer> categoryList;

    /**
     * 定时发布时间
     */
    private String publishTime;

    public static void main(String[] args) {
        MessageParam messageParam = new MessageParam();
        System.out.println(messageParam);
        BaseMessage baseMessage = new MessageParam();
        System.out.println(baseMessage);
    }
}
