package com.demo.server.handler;

import com.demo.protocol.request.LoginRequestPacket;
import com.demo.protocol.respones.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("【服务器】:服务端收到登录请求");

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (valid(loginRequestPacket)) {
            responsePacket.setSuccess(true);
            System.out.println("【服务器】:登陆成功！");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("【服务器】:用户名不存在或密码错误！");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
