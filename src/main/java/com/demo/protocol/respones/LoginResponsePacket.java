package com.demo.protocol.respones;

import com.demo.protocol.Packet;
import lombok.Data;

import static com.demo.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean isSuccess;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
