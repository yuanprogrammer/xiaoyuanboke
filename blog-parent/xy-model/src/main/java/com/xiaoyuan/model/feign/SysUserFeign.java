package com.xiaoyuan.model.feign;

import com.xiaoyuan.model.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "backServer", path = "/back/api/")
public interface SysUserFeign {

    @GetMapping("system/searchUserCode/{articleId}")
    R<String> searchUserCodeByArticleId(@PathVariable(value = "articleId") String articleId);
}
