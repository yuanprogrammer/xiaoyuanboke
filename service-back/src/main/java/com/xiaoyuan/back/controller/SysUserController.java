package com.xiaoyuan.back.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoyuan.back.service.SysUserService;
import com.xiaoyuan.back.constants.WxConstants;
import com.xiaoyuan.common.vo.PageVo;
import com.xiaoyuan.common.pojo.SysUser;
import com.xiaoyuan.common.param.sysuser.SysUserLoginParam;
import com.xiaoyuan.common.param.sysuser.SysUserQueryParam;
import com.xiaoyuan.common.param.sysuser.WeChatRegisterParam;
import com.xiaoyuan.common.vo.R;
import com.xiaoyuan.common.vo.sysuser.SysUserVo;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/system")
@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public R login(@RequestBody @Valid SysUserLoginParam loginParam) {
        return sysUserService.login(loginParam);
    }

    @PostMapping("/getInfo")
    public R getInfo() {
        return sysUserService.getInfo();
    }

    @PostMapping("/logout")
    public R logout() {
        StpUtil.logout();
        return R.success("退出成功");
    }

    @GetMapping("/kickUser/{id}")
    @SaCheckPermission(value = {"USER:DISABLE"}, mode = SaMode.OR)
    public R logoutUserById(@PathVariable Integer id) {
        StpUtil.logout(id);
        return R.success("踢成功");
    }

    @GetMapping("wx_login")
    @ApiOperation("微信登录接口")
    public R exLogin() {
        AuthRequest authRequest = getAuthRequest();
        return R.success().data(authRequest.authorize(AuthStateUtils.createState()));
    }

    @ApiOperation("授权结果回调")
    @RequestMapping("wx_callback")
    public void wxCallback(AuthCallback callback, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        // 回调
        AuthResponse<AuthUser> res = authRequest.login(callback);
        AuthUser authUser = res.getData();

        if (authUser == null) {
            // 跳转到登陆页
            response.sendRedirect(WxConstants.LOGIN_URL);
            return;
        }

        // 获取openId
        String openId = authUser.getRawUserInfo().getString("openid");
        // 构建查询对象, 查询用户
        SysUser sysUser = sysUserService.getSysUserByOpenId(SysUserLoginParam.builder().openId(openId).build());

        if (StrUtil.isEmpty(openId)) {
            // 跳转到登陆页
            response.sendRedirect(WxConstants.LOGIN_URL);
            return;
        }
        if (sysUser != null) {
            // 有账号, 直接登录
            StpUtil.login(sysUser.getId());
            response.sendRedirect(WxConstants.HOME_URL);
        }else {
            // 未登录过, 先注册账户
            WeChatRegisterParam weChatRegisterParam = new WeChatRegisterParam();
            weChatRegisterParam.setOpenId(openId);
            int result = sysUserService.insertSysUserByOpenId(weChatRegisterParam);

            if (result == 0) {
                response.sendRedirect(WxConstants.LOGIN_URL);
            }else {
                StpUtil.login(sysUserService.getSysUserByOpenId(SysUserLoginParam.builder().openId(openId).build()).getId());
                response.sendRedirect(WxConstants.HOME_URL);
            }
        }
    }

    @GetMapping("searchUserCode/{articleId}")
    @ApiOperation(value = "获取系统编号")
    public R<String> searchUserCodeByArticleId(@PathVariable(value = "articleId") String articleId) {
        return new R<String>().data(sysUserService.searchUserCodeByArticleId(articleId));
    }

    @PostMapping("getSysUserList")
    @ApiOperation(value = "获取系统用户列表")
    public R<PageVo<List<SysUserVo>>> getSysUserList(@RequestBody SysUserQueryParam sysUserQueryParam) {
        return sysUserService.getSysUserList(sysUserQueryParam);
    }

    private AuthRequest getAuthRequest() {
        return new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(WxConstants.APP_ID)
                .clientSecret(WxConstants.APP_SECRET)
                .redirectUri(WxConstants.CALLBACK_URL)
                .build());
    }
}
