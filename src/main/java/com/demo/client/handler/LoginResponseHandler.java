package com.demo.client.handler;

import com.demo.protocol.request.LoginRequestPacket;
import com.demo.protocol.respones.LoginResponsePacket;
import com.demo.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】:发起登陆");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("tom");
        packet.setPassword("123");

        ctx.channel().writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            System.out.println("【客户端】:登陆成功");
        } else {
            System.out.println("【客户端】:登陆失败，原因是: " + loginResponsePacket.getReason());
        }
    }
}
