package com.xiaoyuan.qiniu.service;

import com.xiaoyuan.utils.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileName:    UploadService
 * Author:      小袁
 * Date:        2022/4/4 12:22
 * Description:
 */
public interface KodoService {

    /**
     * 上传文件
     * @param file 文件
     * @return
     */
    R uploadArticleImg(MultipartFile file, String folderName);
}
