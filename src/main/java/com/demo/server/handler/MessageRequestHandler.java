package com.demo.server.handler;

import com.demo.protocol.request.MessageRequestPacket;
import com.demo.protocol.respones.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        System.out.println("【服务端】:收到客户端消息：" + messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("【服务端回复】:" + messageRequestPacket.getMessage());

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
