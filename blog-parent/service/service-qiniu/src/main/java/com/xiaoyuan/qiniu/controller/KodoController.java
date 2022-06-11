package com.xiaoyuan.qiniu.controller;

import com.xiaoyuan.qiniu.service.KodoService;
import com.xiaoyuan.qiniu.utils.ConstantPropertiesUtils;
import com.xiaoyuan.utils.constant.HttpStatusEnum;
import com.xiaoyuan.utils.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileName:    UploadController
 * Author:      小袁
 * Date:        2022/4/3 13:20
 * Description:
 */
@RestController
@RequestMapping("/kodo")
@CrossOrigin
@Api(tags = "七牛云文件上传")
public class KodoController {

    private static final String salt = "@#$!@xiaoyuan";

    @Autowired
    private KodoService kodoService;

    @PostMapping("upload/articleImage")
    @ApiOperation(value = "处理文章内容图片的上传")
    public R uploadArticleImg(@RequestHeader(value = "AK") String AK,
                              @RequestHeader(value = "SK") String SK,
                              MultipartFile file,
                              String folderName) {

        // 验证是否非法操作
        if (StringUtils.isBlank(AK) || StringUtils.isBlank(SK)) {
            return R.error(HttpStatusEnum.ILLEGAL_OPERATION);
        }
        if (!(DigestUtils.md5Hex(ConstantPropertiesUtils.ACCESS_KEY + salt).equals(AK)) ||
                !(DigestUtils.md5Hex(ConstantPropertiesUtils.ACCESS_SECRET_KEY + salt).equals(SK))) {
            return R.error(HttpStatusEnum.ILLEGAL_OPERATION);
        }
//        return R.ok().data("url", "https://blog-img.yuanprogrammer.com/347b504d6e6d48a2895330bf8c785869.png");
        return kodoService.uploadArticleImg(file, folderName);
    }
}
