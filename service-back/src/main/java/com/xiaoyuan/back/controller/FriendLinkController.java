package com.xiaoyuan.back.controller;

import com.xiaoyuan.back.service.FriendLinkService;
import com.xiaoyuan.common.pojo.FriendLink;
import com.xiaoyuan.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FileName:    FriendLinkController
 * Author:      小袁
 * Date:        2022/4/30 16:24
 * Description:
 */
@RestController
@RequestMapping("/friendlink")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @PostMapping("")
    public R insertFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.insert(friendLink);
    }

    @DeleteMapping("{id}")
    public R deleteFriendLink(@PathVariable(value = "id") Integer id) {
        return friendLinkService.deleteById(id);
    }

    @PutMapping("")
    public R modifyFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.modifyById(friendLink);
    }

    @GetMapping("{pageIndex}/{pageSize}")
    public R findFriendLinkList(@PathVariable(value = "pageIndex") Integer pageIndex,
                                @PathVariable(value = "pageSize") Integer pageSize) {
        return friendLinkService.listFriendLinkPage(pageIndex, pageSize);
    }
}
