package com.xiaoyuan.back.comm;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaoyuan.back.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/notice/{token}/{sysUserCode}")
public class WebSocketNotice {

    private static SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        WebSocketNotice.sysUserService = sysUserService;
    }

    /**
     * 系统用户编号
     */
    private String sysUserCode;

    // 用来存在线连接用户信息
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token, @PathParam(value = "sysUserCode") String sysUserCode) {
        try {
            // 判断是否有这个用户
            if (StpUtil.getLoginIdByToken(token) != null || sysUserService.isExistUserByCode(sysUserCode)) {
                this.sysUserCode = sysUserCode;
                sessionPool.put(sysUserCode, session);
                log.info("【通知通道】有新的连接，总数为:" + sessionPool.size());
            }else {
                session.getAsyncRemote().sendText("非法连接!");
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            sessionPool.remove(this.sysUserCode);
            log.info("【通知通道】连接断开，总数为:" + sessionPool.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【通知通道】收到客户端消息:" + message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


    // 此为单点消息
    public void sendOneMessage(String sysUserCode, String message) {
        Session session = sessionPool.get(sysUserCode);
        if (session != null && session.isOpen()) {
            try {
                log.info("【通知通道】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isLogin(String sysUserCode) {
        return sessionPool.get(sysUserCode) != null;
    }
}
