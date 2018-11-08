package com.demo.client;

import com.demo.protocol.Packet;
import com.demo.protocol.PacketCodeC;
import com.demo.protocol.request.LoginRequestPacket;
import com.demo.protocol.request.MessageRequestPacket;
import com.demo.protocol.respones.LoginResponsePacket;
import com.demo.protocol.respones.MessageResponsePacket;
import com.demo.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.UUID;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】:发起登陆");

        ByteBufAllocator alloc = ctx.alloc();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("tom");
        packet.setPassword("123");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(alloc, packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;

            if (responsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("【客户端】:登陆成功");
            } else {
                System.out.println("【客户端】:登陆失败，原因是: " + responsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println("【客户端】:收到服务端消息：" + messageResponsePacket.getMessage());

        }
    }
}
