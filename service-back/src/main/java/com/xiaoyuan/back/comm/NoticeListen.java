//package com.xiaoyuan.back.comm;
//
//import cn.hutool.json.JSONUtil;
//import com.rabbitmq.client.Channel;
//import com.xiaoyuan.common.constants.MessageQueueConstants;
//import com.xiaoyuan.common.vo.SysNoticeVo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 通知消费队列监听
// */
//@Slf4j
//@Component
//public class NoticeListen implements ChannelAwareMessageListener {
//
//    @Autowired
//    private WebSocketNotice webSocketNotice;
//
//    @RabbitHandler
//    @RabbitListener(queues = MessageQueueConstants.NOTICE_QUEUE, ackMode = "MANUAL", containerFactory = "noticeListenerFactor")
//    @Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        // 标识
//        long tag = message.getMessageProperties().getDeliveryTag();
//        try {
//            // 消息
//            SysNoticeVo sysNoticeVo = JSONUtil.toBean(new String(message.getBody(), StandardCharsets.UTF_8), SysNoticeVo.class);
//            // 判断该作者是否登录
//            if (webSocketNotice.isLogin(sysNoticeVo.getReceiverCode())) {
//                // 发送消息给前端, 指定作者
//                webSocketNotice.sendOneMessage(sysNoticeVo.getReceiverCode(), JSONUtil.toJsonStr(sysNoticeVo));
//                channel.basicAck(tag, false);
//            }else {
//                // 拒绝消费, true重新回到队列
//                channel.basicReject(tag, true);
//            }
//        }catch (Exception e) {
//            channel.basicNack(tag, false, true);
//        }
//    }
//}
