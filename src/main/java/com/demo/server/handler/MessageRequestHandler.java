package com.demo.server.handler;

import com.demo.protocol.request.MessageRequestPacket;
import com.demo.protocol.respones.MessageResponsePacket;
import com.demo.session.Session;
import com.demo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        //拿到消息发送方的session
        Session session = SessionUtil.getSession(ctx.channel());

        //构造消息响应对象
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //拿到消息接收方的channel
        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //发送响应对象
        if (channel != null && SessionUtil.hasLogin(channel)) {
            channel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + session.getUserId() + "] 不在线，发送失败!");
        }
    }
}
