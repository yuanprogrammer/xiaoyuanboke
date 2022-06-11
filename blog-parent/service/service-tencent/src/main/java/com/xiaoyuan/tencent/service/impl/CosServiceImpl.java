package com.xiaoyuan.tencent.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.xiaoyuan.tencent.service.CosService;
import com.xiaoyuan.tencent.utils.ConstantPropertiesUtils;
import com.xiaoyuan.utils.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * FileName:    CosServiceImpl
 * Author:      小袁
 * Date:        2022/4/5 13:17
 * Description:
 */
@Service
public class CosServiceImpl implements CosService {

    private static final String COS_URL_PREFIX = "https://xiaoyuan-1305930739.cos.ap-guangzhou.myqcloud.com/";

    @Override
    public R upload(MultipartFile file) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = ConstantPropertiesUtils.SECRET_ID;
        String secretKey = ConstantPropertiesUtils.SECRET_KEY;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 存储桶
        String bucketName = 存储桶名称;

        // 按照日期分类存储
        String time = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        // 对象键(Key)是对象在存储桶中的唯一标识。 ————>> 文件名
        String key = time + "/" + uuid + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        // 设置 bucket 的地域
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);

            // 上传
            cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } finally {
            // 确认本进程不再使用 cosClient 实例之后，关闭之
            cosClient.shutdown();
        }

        return R.ok().data("url", COS_URL_PREFIX + key);
    }
}
