package com.xiaoyuan.qiniu.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xiaoyuan.qiniu.service.KodoService;
import com.xiaoyuan.qiniu.utils.ConstantPropertiesUtils;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * FileName:    UploadServiceImpl
 * Author:      小袁
 * Date:        2022/4/4 12:23
 * Description:
 */
@Service
public class KodoServiceImpl implements KodoService {

    private static String ARTICLE_IMG_URL_PREFIX = "七牛云kodo存储地址前缀";

    @Override
    public R uploadArticleImg(MultipartFile file, String folderName) {
        String accessKey = ConstantPropertiesUtils.ACCESS_KEY;
        String secretKey = ConstantPropertiesUtils.ACCESS_SECRET_KEY;
        String articleImgBucket = ConstantPropertiesUtils.ARTICLE_IMG_BUCKET;

        // 构造一个带指定 Region 对象的配置类 ----- 华南区域
        Configuration cfg = new Configuration(Region.huanan());

        UploadManager uploadManager = new UploadManager(cfg);

        // 通过UUID生成唯一文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        String key = "";
        if (StringUtils.isAnyBlank(folderName)) {
            key = fileName.replaceAll("-", "");
        }else {
            key = folderName + "/" + fileName.replaceAll("-", "");
        }

        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(articleImgBucket);
            try {
                Response response = uploadManager.put(file.getBytes(), key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            } catch (IOException e) {
                e.printStackTrace();
                return R.error(HttpStatusEnum.UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(HttpStatusEnum.UNKNOWN_ERROR);
        }

        return R.ok().data("url", ARTICLE_IMG_URL_PREFIX + key + "~articleImg");
    }
}
