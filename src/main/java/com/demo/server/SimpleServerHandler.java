package com.demo.server;

import com.demo.protocol.Packet;
import com.demo.protocol.PacketCodeC;
import com.demo.protocol.request.LoginRequestPacket;
import com.demo.protocol.respones.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            System.out.println("【服务器】:服务端收到登录请求");

            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            if (valid(requestPacket)) {
                responsePacket.setSuccess(true);
                System.out.println("【服务器】:登陆成功！");
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("【服务器】:用户名不存在或密码错误！");
            }
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(response);
        }

    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }

}
