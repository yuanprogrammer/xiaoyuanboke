package com.xiaoyuan.tencent.service;

import com.xiaoyuan.utils.vo.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FileName:    CosService
 * Author:      小袁
 * Date:        2022/4/5 13:17
 * Description:
 */
public interface CosService {

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    R upload(MultipartFile file);
}
