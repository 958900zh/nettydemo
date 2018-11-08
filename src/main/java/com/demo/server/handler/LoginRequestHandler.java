package com.demo.server.handler;

import com.demo.protocol.request.LoginRequestPacket;
import com.demo.protocol.respones.LoginResponsePacket;
import com.demo.session.Session;
import com.demo.util.LoginUtil;
import com.demo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("【服务器】:服务端收到登录请求");

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (valid(loginRequestPacket)) {
            String userId = randomUserId();
            responsePacket.setUserId(userId);
            responsePacket.setUserName(loginRequestPacket.getUserName());
            responsePacket.setSuccess(true);
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
            System.out.println("【服务器】:登陆成功！");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("【服务器】:用户名不存在或密码错误！");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
