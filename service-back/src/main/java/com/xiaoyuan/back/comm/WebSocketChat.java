package com.xiaoyuan.back.comm;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaoyuan.back.service.SysUserService;
import com.xiaoyuan.common.param.MessageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/chat/{token}/{sysUserCode}")
public class WebSocketChat {

    private static SysUserService sysUserService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        WebSocketChat.sysUserService = sysUserService;
    }

    /**
     * 系统用户编号
     */
    private String sysUserCode;

    // 存储离线消息列表
    private static final ConcurrentHashMap<String, LinkedList<MessageParam>> sessionUnreadPool = new ConcurrentHashMap<>();

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
//    private static final CopyOnWriteArraySet<WebSocketChat> webSockets = new CopyOnWriteArraySet<>();

    // 用来存在线连接用户信息
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token, @PathParam(value = "sysUserCode") String sysUserCode) {
        try {
            if (StpUtil.getLoginIdByToken(token) != null && sysUserService.isExistUserByCode(sysUserCode)) {
                this.sysUserCode = sysUserCode;
                sessionUnreadPool.computeIfAbsent(sysUserCode, k -> new LinkedList<>());
                sessionPool.put(sysUserCode, session);
                log.info("====================================================");
                log.info("【聊天通道】有新的连接, 编号({}), 当前在线人数:{}人", this.sysUserCode, sessionPool.size());
                log.info("====================================================");
            }else {
                session.getAsyncRemote().sendText("非法连接!");
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (sessionPool.size() != 0 && this.sysUserCode != null) {
            sessionPool.remove(this.sysUserCode);
            log.info("====================================================");
            log.info("【聊天通道】连接断开, 编号({}), 当前在线人数:{}人", this.sysUserCode, sessionPool.size());
            log.info("====================================================");
            this.sysUserCode = null;
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        JSONObject msgJson = JSONUtil.parseObj(message);
        String receiverCode = msgJson.getStr("toContactId");
        String senderCode = msgJson.getJSONObject("fromUser").getStr("id");
        switch (msgJson.getInt("msgType")) {
            // 单人聊天
            case 5:
                // 解析消息体
                MessageParam messageParam = JSONUtil.toBean(message, MessageParam.class);
                messageParam.setMsgSendTime(DateUtil.formatDateTime(DateUtil.date(messageParam.getSendTime())));
                messageParam.setStatus("succeed");
                // 查看对方是否在线, 在线->直接发送, 不在线->离线存储
                if (isLogin(receiverCode)) {
                    // 发送给对方
//                    messageParam.setToContactId(senderCode);
                    List<MessageParam> unreadChatMessage = new ArrayList<>();
                    unreadChatMessage.add(messageParam);
                    sendOneMessage(receiverCode, JSONUtil.toJsonStr(unreadChatMessage), false);
                }else {
                    // 离线存储
                    LinkedList<MessageParam> unreadChatMessageList = sessionUnreadPool.get(receiverCode) == null ? new LinkedList<>() : sessionUnreadPool.get(receiverCode);
                    unreadChatMessageList.add(messageParam);
                    sessionUnreadPool.put(receiverCode, unreadChatMessageList);
                }
                break;
            case 6:
                break;
            case 7:
                // 读取离线消息
                LinkedList<MessageParam> unreadChatMessageList = sessionUnreadPool.get(this.sysUserCode);
                // 筛选当前两个用户的消息
                List<MessageParam> currentUnreadChatMessage = new ArrayList<>();
                Iterator<MessageParam> it = unreadChatMessageList.iterator();
                while (it.hasNext()) {
                    MessageParam unreadMessageParam = it.next();
                    if (unreadMessageParam.getFromUser().getId().equals(senderCode)) {
//                        unreadMessageParam.setToContactId(unreadMessageParam.getFromUser().getId());
                        currentUnreadChatMessage.add(unreadMessageParam);
                        // 移除
                        it.remove();
                    }
                }

                // 更新未读消息
                sessionUnreadPool.put(this.sysUserCode, unreadChatMessageList);
                // 发送消息
                if (currentUnreadChatMessage.size() != 0) {
                    sendOneMessage(this.sysUserCode, JSONUtil.toJsonStr(currentUnreadChatMessage), true);
                }
                break;
        }
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("====================================================");
        log.error("【聊天通道】连接失败, 原因: {}", error.getMessage());
        log.info("====================================================");
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【聊天通道】广播消息:" + message);
        for (String sysUserCode : sessionPool.keySet()) {
            try {
                if (sessionPool.get(sysUserCode).isOpen()) {
                    sessionPool.get(sysUserCode).getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String sysUserCode, String message, boolean isUnread) {
        Session session = sessionPool.get(sysUserCode);
        if (session != null && session.isOpen()) {
            log.info("====================================================");
            log.info("【聊天通道】发送{}消息", isUnread ? "离线" : "在线");
            log.info("消息发送方编号: {}", this.sysUserCode);
            log.info("消息接收方编号: {}", sysUserCode);
            log.info("消息内容: {}", message);
            log.info("====================================================");
            session.getAsyncRemote().sendText(message);
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【聊天通道】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isLogin(String sysUserCode) {
        return sessionPool.get(sysUserCode) != null;
    }
}
